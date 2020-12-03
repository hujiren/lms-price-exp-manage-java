#!/bin/bash

#项目绝对路径
projectPath="/Volumes/data/java/apl-sys"

#远程目标路径
remoteRootPath="";

hostAddr="192.168.1.185"
userId="root"

if [ "$hostIndex" -eq "2" ]; then
    hostAddr="8.129.236.174"
    userId="aapl"
    remoteRootPath="/home/aapl/java/";
fi

if [ "$hostIndex" -eq "3" ]; then
    hostAddr="8.129.236.175"
    userId="aapl"
    remoteRootPath="/home/aapl/java/";
fi

echo "远程主机: $hostAddr"

filePath=$1
fileName=$2
remotePath=$3
port=$4

if [[ $fileName == *"jar" ]];then
      #jar
      echo  "远程关闭 $fileName..."
      sshpass -p $password ssh  -o StrictHostKeyChecking=no $userId@$hostAddr "cd $remoteRootPath$remotePath; cp ./$fileName ./bak/; ./startup.sh stop $fileName; "

      echo  "上传 $fileName..."
      #scp  $projectPath/$filePath$fileName  $userId@$hostAddr:$remoteRootPath$remotePath
      sshpass -p $password scp  $projectPath/$filePath$fileName  $userId@$hostAddr:$remoteRootPath$remotePath

      echo  "远程启动 $fileName..."
      if [ -z "$port" ]; then
         sshpass -p $password ssh  $userId@$hostAddr "source /etc/profile; cd $remoteRootPath$remotePath; nohup java -jar  $fileName  --server.port=$port  >./logs/$fileName.log 2>&1 &"
      else
         sshpass -p $password ssh  $userId@$hostAddr "source /etc/profile; cd $remoteRootPath$remotePath; nohup java -jar  $fileName  >./logs/$fileName.log 2>&1 &"
      fi

      echo  "上传 $fileName, 并远程启动完成!"
  else
      #非jar
      echo  "上传 $fileName..."
      #scp  $projectPath/$filePath$fileName  $userId@$hostAddr:$remoteRootPath$remotePath
      sshpass -p $password scp  $projectPath/$filePath$fileName  $userId@$hostAddr:$remoteRootPath$remotePath

      echo  "上传 $fileName, 完成!"
fi



