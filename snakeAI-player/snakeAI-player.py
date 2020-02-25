#!/bin/sh

# Snake AI player
# Developed by Ariel Lima
# Copyright. All rights reserved © 

import os
from subprocess import Popen, PIPE
import pathlib
import time

# Getting game path to change the main directory
game_path = (str(pathlib.Path(pathlib.Path(__file__).parent.absolute()).parent.absolute()) + "/snakeAIGame/bin")#.replace(" ", "\ ")

# Changing the main directory to the game directory (ready to execute the java)
os.chdir(game_path)

# Opening the game using JRE
game = Popen(['java', 'snakeAIGame.snakeAIGameMain'], stdin=PIPE, stdout=PIPE, text=True)

# Method to send and receive data from the process (if you send data and don't receive the program will stuck here)
def command(process, data):
    data = str(data)
    if not data.endswith("\n"):
        data = data + "\n"
    process.stdin.write(data)
    process.stdin.flush()
    return process.stdout.readline()

# Main loop

time_between_actions = command(game, "RUN")
print('Time beetween actions: ' + time_between_actions)
time_between_actions = float(time_between_actions) / 1000.0
time.sleep(time_between_actions)
while True:
    for i in range(5): 
        output = command(game, "RIGHT")
        print(output)
        time.sleep(time_between_actions)
    for i in range(5): 
        output = command(game, "DOWN")
        print(output)
        time.sleep(time_between_actions)
    for i in range(5): 
        output = command(game, "LEFT")
        print(output)
        time.sleep(time_between_actions)
    for i in range(5): 
        output = command(game, "UP")
        print(output)
        time.sleep(time_between_actions)

        
    

