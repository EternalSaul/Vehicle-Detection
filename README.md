# Vehicle-Detection

<div align="center">

<img src="https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/jk3.jpg" width="50%"/>

<img src="https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/predictions.png" width="50%"/>

</div>

This is a very simple demo of vehicle detection,I use JavaFx to make a very simple GUI.

When you're using this gui to detect vehicle video or images, it's actually calling darknet.exe by command line.

I think you would like darknet! You can get more information about it in  [here (Original)](https://github.com/pjreddie/darknet) or [here (AlexeyAB)](https://github.com/AlexeyAB/darknet/) ,the darknet.exe of this repository compiled by vs2015 and use src of AlexeyAB's repository.

My environment  is CUDA 9.1 with cuDNN 7.0.And OpenCV 3.0 .You can download the *.dll in [here](https://pan.baidu.com/s/1WhfZvOfj56nLKdCz6C9qNA) with the password: **ijek**

If you dont have NV GPU, you can use darknet_no_gpu.exe rename to darknet.exe

We can use darknet to train detector easyly with two amazing detection methods：Yolo v2 and Yolo v3.

Yolo v2:

![Q图片2018061009340](https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/QQ图片20180610093400.png)

Yolo v3:

![Q图片2018061119410](https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/QQ图片20180611194105.png)

I use Pre-training model to train four detector on UA-DETRACT training set.

1.single class Yolo v2 detector

2.four-classes Yolo v2 detector

3.single class Yolo v3 detector

4.four-classes Yolo v3 detector

Single class detector only detect car,but four-classes detector detect four kinds of car includes car,van,bus,others.You can download these in [here](https://pan.baidu.com/s/1WhfZvOfj56nLKdCz6C9qNA) with the password: **ijek**

UADETRAC-TrainSet：

|       | car            | van            | bus            | others        |
| ----- | -------------- | -------------- | -------------- | ------------- |
| count | 503853(84.21%) | 57051（9.53%） | 33651（5.62%） | 3726（0.62%） |

The model I trained is not good enough.This is PR curve of my detector with others detectors at UA-DETRAC website:

![Q图片2018061117151](https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/QQ图片20180611171515.png)



Yolo is very Fast!Yolo v2 detector achive 54 FPS on GTX1070,and Yolo v3 detector achive 29.4 FPS because it uses a deeper network to achive higher mAP on test benchmark.And Yolo v2 and v3 detector can achive 16 an 6.9 FPS separately on GTX860m.



<div align="center">

Train Sample

![训练样本实例](https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/QQ图片20180409000739.png)



Detect Simple(Yolo v2 four-classes)：

![adetract-tes](https://raw.githubusercontent.com/EternalSaul/Vehicle-Detection/master/img/uadetract-test.png)

</div>















