
from hashlib import new
import sqlite3 as sql
from sqlite3 import Error
from datetime import datetime as d

def db_file():
    return str("sqlitesample.db")


connection = None
try:
    connection = sql.connect(db_file())
    print(sql.version)
    cursor = connection.cursor()
    cursor.execute(
        """
        INSERT INTO Moderator (email)
VALUES ('mod1@backpacking.com');
        """)
        
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