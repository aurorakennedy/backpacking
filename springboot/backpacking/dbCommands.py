
from hashlib import new
import sqlite3 as sql
from sqlite3 import Error
from datetime import datetime as d

def db_file():
    return str("database.db")


connection = None
try:
    connection = sql.connect(db_file())
    print(sql.version)
    cursor = connection.cursor()
    cursor.execute(
        """
        CREATE TABLE User (
  email VARCHAR(50) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  username VARCHAR(20) NOT NULL
);""")
        
    connection.commit()
except Error as e:
    print(e)
finally:
    if connection:
        connection.close()



# connection = None
# data = []
# try:
#     connection = sql.connect(db_file())
#     print(sql.version)
#     cursor = connection.cursor()
#     cursor.execute("""
#             SELECT *
#             FROM User
            
#         """)
#     data = cursor.fetchall()
        
# except Error as e:
#         print(e)
# finally:
#     if connection:
#             connection.close()
# print(data) 
        
print("Done")