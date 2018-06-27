import sqlite3
import time
import datetime
date_stamp = str(datetime.datetime.fromtimestamp(int(time.time())).strftime('%Y-%m-%d %H:%M:%S'))
def  usr_info(phoneNumber):
    usr = phoneNumber + '.db'
    conn = sqlite3.connect(usr)
    cur = conn.cursor()
    cur.execute("CREATE TABLE IF NOT EXISTS violation (time, fee, location, violated)")
    cur.execute("CREATE TABLE IF NOT EXISTS roadfee (time, fee, location)")
    cur.execute("CREATE TABLE IF NOT EXISTS track (time, location)")
    return conn, cur
    
def violated_write(phoneNumber, fv, lv, vv):
    conn, cur = usr_info(phoneNumber)
    tv = date_stamp
    cur.execute("INSERT INTO violation (time, fee, location, violated) VALUES(?,?,?,?)",
                     (tv, fv, lv, vv))
    conn.commit()
    cur.close()
    conn.close()
        
        
def roadfee_write(phoneNumber, frf, lrf):
    conn, cur = usr_info(phoneNumber)
    tv = date_stamp
    trf = date_stamp
    cur.execute("INSERT INTO roadfee (time, fee, location) VALUES(?,?,?)",
                     (trf, frf, lrf))
    conn.commit()
    cur.close()
    conn.close()


def track_write(phoneNumber, lt):
    conn, cur = usr_info(phoneNumber)
    tt = date_stamp
    cur.execute("INSERT INTO track (time, location) VALUES(?,?)",
                     (tt, lt))
    conn.commit()
    cur.close()
    conn.close()

def violated_read(phoneNumber):
    conn, cur = usr_info(phoneNumber)
    cur.execute("SELECT * FROM violation")
    data = cur.fetchall()
    cur.close()
    conn.close()
    return data

def roadfee_read(phoneNumber):
    conn, cur = usr_info(phoneNumber)
    cur.execute("SELECT * FROM roadfee")
    data = cur.fetchall()
    cur.close()
    conn.close()
    return data

def track_read(phoneNumber):
    conn, cur = usr_info(phoneNumber)
    cur.execute("SELECT * FROM track")
    data = cur.fetchall()
    cur.close()
    conn.close()
    return data
    

def usr_manager():
    conn = sqlite3.connect('User_database.db')
    cur = conn.cursor()
    cur.execute("CREATE TABLE IF NOT EXISTS userData (phoneNumber , licensePlate, password)")
    return conn, cur

def usr_write(phoneNumber, licensePlate, password):
    conn, cur = usr_manager()
    cur.execute("INSERT INTO userData (phoneNumber, licensePlate, password) VALUES(?,?,?)", (phoneNumber, licensePlate, password))
    conn.commit()
    cur.close()
    conn.close()

def  usr_read():
    conn, cur = usr_manager()
    cur.execute("SELECT * FROM userData")
    data = cur.fetchall()
    cur.close()
    conn.close()
    return data
