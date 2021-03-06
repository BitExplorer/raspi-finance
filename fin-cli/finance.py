#!/usr/bin/env python3
"""example python3
"""
import sys
import json
import os
import time
import requests
import time
from datetime import date
from datetime import datetime
#from dataclasses import dataclass

def computeDateDoy( year, month, mday ):
  n1 = int((275 * month) / 9)
  n2 = int((month + 9) / 12)
  n3 = (1 + int((year - 4 * int(year / 4) + 2) / 3))
  n = n1 - (n2 * n3) + mday - 30
  return n

def datetimeToEpoch ( years, months, mday, hours, mins, secs ):
  epoch_start = 1970
  total_days = 0
  total_secs = 0

  while ( epoch_start < years ):
    total_days = total_days + computeDateDoy(epoch_start, 12, 31)
    epoch_start = epoch_start + 1
  total_days = total_days + computeDateDoy(years, months, mday - 1)
  total_secs = (total_days * 86400) + (hours * 60 * 60) + (mins * 60) + secs
  return total_secs

#You will need to convert your instance in python dict and then you can dump that dict in json.dumps(instance_dict).
#@dataclass  
class Transaction:
  def __init__(self, guid, sha256, accountType, accountNameOwner, description, category, notes, cleared, reoccurring, amount, transactionDate, dateUpdated, dateAdded ):
    self.guid = guid
    self.sha256 = sha256
    self.accountType = accountType
    self.accountNameOwner = accountNameOwner
    self.description = description
    self.category = category
    self.notes = notes
    self.cleared = cleared
    self.reoccurring = reoccurring
    self.amount = amount
    self.transactionDate = transactionDate
    self.dateUpdated = dateUpdated
    self.dateAdded = dateAdded
  @property
  def seven_mult(self):
    return self.amount * 7
  def __str__(self):
    return json.dumps(self.__dict__)

def fileRead(ifname):
  with open(ifname, 'r') as ifp:
    #row = ifp.readline()
    #elements = row.split("\t")
    fileContent = ifp.read()
  ifp.closed
  return fileContent

def separateByRow(fileContent):
  rows = fileContent.splitlines()
  return rows

def processRow(row):
  cols = row.split("\t")
  if len(cols) == 8:
    print(cols[0] + cols[1] + cols[2] + cols[3] + cols[4] + cols[5] + cols[6] + cols[7])
    transaction = Transaction('', '', 'credit', cols[1], cols[3], cols[4], cols[7], cols[6], 'false', cols[5], cols[2], '0', '0')
    print(transaction)
  else:
    print(len(cols))

def main():
    """
    Entry Point.
    """
    if len(sys.argv) != 1:
      print("Usage: %s <noargs>" % sys.argv[0])
      sys.exit(1)
    #transaction = Transaction('', '', 'credit', 'amex_brian', 'example', '', '', '0', 'false', '0.1', '0', '0', '0')
    #jsonObj = json.dumps(transaction.__dict__)
    #print(jsonObj)
    #print(transaction)
    fileContent = fileRead("input.txt")
    rows = separateByRow(fileContent)
    for idx in range(len(rows)):
      processRow(rows[idx])
    #response = requests.get('http://localhost:8080/transactions/getTransaction/123', auth=('user', 'password'))
    #response = requests.get('http://localhost:8080/transactions/getTransaction/340c315d-39ad-4a02-a294-84a74c1c7ddc')
    response = requests.get('http://localhost:8080/transactions/getTransaction/04ca4498-fa41-47f2-b501-9084e021998b')
    data = response.json()
    print(data)
    #fh = open("input.txt", "r") 
    #print fh.readline()
    sys.exit(0)

main()
