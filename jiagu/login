#!/bin/sh
BASEDIR=`dirname $0`
#FIRM=$BASEDIR/firm
#INSTALLJAR=$BASEDIR/install.jar
#if [ ! -d $FIRM ]; then  
#	printf "firm目录不存在\n"
#	cd $BASEDIR
#	printf "开始解压\n"
#	exec java -jar $INSTALLJAR
#else
#	printf "firm目录存在"
#	cd $BASEDIR/firm
#	exec java -jar -XstartOnFirstThread main.jar
#fi

	cd $BASEDIR
	printf "curPath: $BASEDIR\n"
	exec java/bin/java -Dapple.laf.useScreenMenuBar=true -Dcom.apple.macos.use-file-dialog-packages=true -Xdock:name=360加固助手 -jar jiagu.jar