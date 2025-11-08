#!/bin/bash
pid=$(lsof -i:8080 | awk 'NR==2{print $2}')
if [ ! -z "$pid" ]; then
  kill -9 $pid
fi

nohup java -jar ./yun-picture-1.0.0.jar > nohup.out 2>&1 &
