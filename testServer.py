
# import the necessary packages
from imutils.perspective import four_point_transform
from imutils import contours
import numpy as np
import argparse
import imutils
import cv2
import watchdog
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
import time
import utlis

########################################################################

heightImg = 690
widthImg = 700
ans = [0, 1, 2, 3, 4,3,2,1,0,1,2,3,4,3,2,1,0]
########################################################################

def process (path):

    file_path=path
    file=open(file_path,'r')
    lines=file.readlines()
    img_path=lines[0]
    img_path=img_path.strip()
    questions = int(lines[1])
    choices = 5
    if(questions==15):
        heightImg=690
    elif(questions==20):
        heightImg=700
    elif(questions==25):
        heightImg=700
    else:
        fileName = path.split('\\')[-1]
        fileName = fileName.split('.')[0]
        f = open('E:\\FinalProject\\Outputs\\' + fileName + '.txt', "w+")
        f.write("-1")
    print(img_path, questions)
    img = cv2.imread(img_path)

    img = cv2.resize(img, (widthImg, heightImg))  # RESIZE IMAGE

    imgFinal = img.copy()
    imgBlank = np.zeros((heightImg, widthImg, 3), np.uint8)  # CREATE A BLANK IMAGE FOR TESTING DEBUGGING IF REQUIRED
    imgGray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # CONVERT IMAGE TO GRAY SCALE
    imgBlur = cv2.GaussianBlur(imgGray, (5, 5), 1)  # ADD GAUSSIAN BLUR
    imgCanny = cv2.Canny(imgBlur, 10, 70)  # APPLY CANNY

    try:
        ## FIND ALL COUNTOURS
        imgContours = img.copy()  # COPY IMAGE FOR DISPLAY PURPOSES
        imgBigContour = img.copy()  # COPY IMAGE FOR DISPLAY PURPOSES
        contours, hierarchy = cv2.findContours(imgCanny, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)  # FIND ALL CONTOURS
        cv2.drawContours(imgContours, contours, -1, (0, 255, 0), 10)  # DRAW ALL DETECTED CONTOURS
        rectCon = utlis.rectContour(contours)  # FILTER FOR RECTANGLE CONTOURS
        biggestPoints = utlis.getCornerPoints(rectCon[0])  # GET CORNER POINTS OF THE BIGGEST RECTANGLE
        #gradePoints = utlis.getCornerPoints(rectCon[1])  # GET CORNER POINTS OF THE SECOND BIGGEST RECTANGLE

        if biggestPoints.size != 0 :

            # BIGGEST RECTANGLE WARPING
            biggestPoints = utlis.reorder(biggestPoints)  # REORDER FOR WARPING
            cv2.drawContours(imgBigContour, biggestPoints, -1, (0, 255, 0), 20)  # DRAW THE BIGGEST CONTOUR
            pts1 = np.float32(biggestPoints)  # PREPARE POINTS FOR WARP
            pts2 = np.float32([[0, 0], [widthImg, 0], [0, heightImg], [widthImg, heightImg]])  # PREPARE POINTS FOR WARP
            matrix = cv2.getPerspectiveTransform(pts1, pts2)  # GET TRANSFORMATION MATRIX
            imgWarpColored = cv2.warpPerspective(img, matrix, (widthImg, heightImg))  # APPLY WARP PERSPECTIVE


            # APPLY THRESHOLD
            imgWarpGray = cv2.cvtColor(imgWarpColored, cv2.COLOR_BGR2GRAY)  # CONVERT TO GRAYSCALE
            imgAdaptiveTh = cv2.adaptiveThreshold(imgWarpGray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
            imgThresh = cv2.threshold(imgAdaptiveTh, 170, 255, cv2.THRESH_BINARY_INV)[1]  # APPLY THRESHOLD AND INVERSE


            boxes = utlis.splitBoxes(imgThresh,questions)  # GET INDIVIDUAL BOXES
            countR = 0
            countC = 0
            myPixelVal = np.zeros((questions, choices))  # TO STORE THE NON ZERO VALUES OF EACH BOX
            for image in boxes:
                #cv2.imshow(str(countR)+str(countC),image)
                totalPixels = cv2.countNonZero(image)
                myPixelVal[countR][countC] = totalPixels
                #cv2.imshow("Split Test ", image)
                #print("hi",myPixelVal[countR][countC])
                #cv2.waitKey(0)
                countC += 1
                if (countC == choices): countC = 0;countR += 1

            # FIND THE USER ANSWERS AND PUT THEM IN A LIST
            myIndex = []
            for x in range(0, questions):
                arr = myPixelVal[x]
                myIndexVal = np.where(arr == np.amax(arr))
                myIndex.append(myIndexVal[0][0])


            # COMPARE THE VALUES TO FIND THE CORRECT ANSWERS
            answers=''
            #grading = []
            #print("hi")
            for x in range(0, questions):
                answers+=str(myIndex[x])
                #if ans[x] == myIndex[x]:
                    #grading.append(1)
                #else:
                    #grading.append(0)
            # print("GRADING",grading)
            #score = (sum(grading) / questions) * 100  # FINAL GRADE
            #print("SCORE",score)

            #result =str(myIndex)
            print("USER ANSWERS", answers)
            fileName = path.split('\\')[-1]
            fileName = fileName.split('.')[0]
            f = open('E:\\FinalProject\\Outputs\\' + fileName + '.txt', "w+")
            f.write(answers)
    except:
        imageArray = ([img, imgGray, imgCanny, imgContours],
                      [imgBlank, imgBlank, imgBlank, imgBlank])
        print("Exception")
        fileName = path.split('\\')[-1]
        fileName = fileName.split('.')[0]
        f = open('E:\\FinalProject\\Outputs\\' + fileName + '.txt', "w+")
        f.write("-1")


class MyHandler(FileSystemEventHandler):
	def on_created(self, event):
		time.sleep(2)
		print(event.src_path);
		process(event.src_path);

event_handler = MyHandler()
observer = Observer()
observer.schedule(event_handler, path='E:\\FinalProject\\InputFiles', recursive=False)
observer.start()
try:
	while True:
		time.sleep(1)
except KeyboardInterrupt:
	observer.stop()
observer.join()

print("Closed")