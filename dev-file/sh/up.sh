#!/bin/bash

read -t 30 -p "Please choose host 1、192.168.1.185    2、8.129.236.174:" hostIndex

echo "Please input your password:"
read -t 30 -s password


source ./up-start.sh  "apl-lms-price-exp-manage-impl-master/apl-lms-price-exp-manage-service/src/main/resources/mapper" "/*.xml"  "resources/mapper/lms-exp-price-manage/mapper"

source ./up-start.sh  "apl-lms-price-exp-manage-impl-master/apl-lms-price-exp-manage-service/src/main/resources/mapper2" "/*.xml"  "resources/mapper/lms-exp-price-manage/mapper2"

source ./up-start.sh  "apl-lms-price-exp-manage-impl-master/apl-lms-price-exp-manage-app/target/" "apl-lms-price-exp-manage-app-1.0.0.jar" ""
