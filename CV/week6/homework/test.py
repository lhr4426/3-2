import cv2
import numpy as np
from cv2 import KeyPoint

def main():
    src = cv2.imread("CV/week6/homework/zebra.png")
    src_rotation = cv2.rotate(src, cv2.ROTATE_90_CLOCKWISE)

    src = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
    src_rotation = cv2.cvtColor(src_rotation, cv2.COLOR_BGR2GRAY)

    sift = cv2.SIFT_create()

    kp1, des1 = sift.detectAndCompute(src, None)
    kp2, des2 = sift.detectAndCompute(src_rotation, None)

    ## Matching 부분 ## 
    bf = cv2.BFMatcher_create()
    matches = bf.match(des1, des2, k = 2)
    matches = sorted(matches, key = lambda x:x.distance)

    result = cv2.drawMatches(src, kp1, src_rotation, kp2, matches[:20], outImg=None, flags=2)
    
    # 결과의 학번 작성하기!
    cv2.imshow('20211924_my_sift', result) 
    cv2.waitKey()
    cv2.destroyAllWindows()
    

if __name__ == '__main__':
    main()