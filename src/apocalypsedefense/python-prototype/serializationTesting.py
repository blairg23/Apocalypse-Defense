#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Pat
#
# Created:     26/03/2012
# Copyright:   (c) Pat 2012
# Licence:     <your licence>
#-------------------------------------------------------------------------------
#!/usr/bin/env python

import pickle
import json # see http://stackoverflow.com/questions/3679306/easy-json-encoding-with-python
class Tower:
    def __init__(self):
        self.attack = 10

class Map:
    def __init__(self):
        self.creeps = ["one string creep in a list"]
        self.towers = [Tower()]

def main():
    m = Map()

    # Can't serialize objects that don't subclass jsonthing
    print json.dumps(m.creeps, indent=2)
    for tower in m.towers:
        print json.dumps(tower.attack, indent=2)
##[
##  "one string creep in a list"
##]
##10

def doPickleSerialization(o):
    p = pickle.dumps(o)
    print p

if __name__ == '__main__':
    main()

# Pickle proto 0 looks like:
##(i__main__
##Map
##p0
##(dp1
##S'towers'
##p2
##(lp3
##(i__main__
##Tower
##p4
##(dp5
##S'attack'
##p6
##I10
##sbasS'creeps'
##p7
##(lp8
##S'one string creep in a list'
##p9
##asb.