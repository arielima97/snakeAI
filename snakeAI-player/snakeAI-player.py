#!/bin/sh
import os
from subprocess import Popen, PIPE
import pathlib
import errno
import sys

game_path = (str(pathlib.Path(pathlib.Path(__file__).parent.absolute()).parent.absolute()) + "/snakeAIGame/bin")#.replace(" ", "\ ")
os.chdir(game_path)
game = Popen(['java', 'snakeAIGame.snakeAIGameMain'], stdin=PIPE, stdout=PIPE, text=True)

def command(process, data):
    data = str(data)
    if not data.endswith("\n"):
        data = data + "\n"
    game.stdin.write(data)
    game.stdin.flush()
    return game.stdout.readline()