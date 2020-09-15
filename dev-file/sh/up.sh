
#项目绝对路径
MYPATH="/Volumes/data/java/apl-lms/apl-lms-price-exp-manage-java"

scp  $MYPATH/apl-lms-price-exp-manage-impl-master/apl-lms-price-exp-manage-service/src/main/resources/mapper/*.xml  root@192.168.1.185:/usr/local/java/basic/resource/mapper/lms-price-exp-manage

scp  $MYPATH/apl-lms-price-exp-manage-impl-master/apl-lms-price-exp-manage-app/target/apl-lms-price-exp-manage-app-1.0.0.jar root@192.168.1.185:/usr/local/java/basic/