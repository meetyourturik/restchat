#!/bin/bash

path=$(dirname "$0")

cd $path/../

mvn -T 1C clean install

if [ $? -gt 0 ]; then
    read -rsp $'Ошибка компиляции! См. подробности в логах. Нажмите любую клавишу...\n' -n 1 key
fi
