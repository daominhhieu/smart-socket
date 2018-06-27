from data_manager import  violated_write,  roadfee_write, track_write
for i in range(10):
    violated_write("01627690208",i,"Dormitory","nothing")
    roadfee_write("01627690208",i,"Dormitory")
    track_write("01627690208","Dormitory")
