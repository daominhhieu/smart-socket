from flask import Flask, render_template, request, flash, url_for, redirect, make_response
app = Flask(__name__)
from data_manager import  violated_read, roadfee_read, track_read, usr_write, usr_read

@app.errorhandler(404)
def page_not_found(e):
    return render_template("404.html")

@app.errorhandler(405)
def page_not_found(e):
    return render_template("405.html")

@app.errorhandler(500)
def page_not_found(e):
    return render_template("500.html")

@app.route('/signup/', methods=['GET','POST'])
def signup():
    error = ''
    number_exists = False
    license_exists = False
    x = usr_read()
    try:
        if request.method == "POST":
            sign_number     = request.form['sign_phone_number']
            license_plate   = request.form['license_plate']
            sign_password   = request.form['sign_password']
            retype_password = request.form['retype_password']

            for i in range(len(x)):
                if sign_number == x[i][0]:
                    number_exists = True
                    break
            for j in  range(len(x)):
                if license_plate == x[j][1]:
                    license_exists = True
                    break 

            if( (len(sign_number) == 10 or len(sign_number) == 11) and sign_number.isdigit() == True and number_exists == False ):
                error = 'Vailid Phone Number'
                    
                if( len(sign_password) > 5 and sign_password == retype_password and sign_password.isdigit() == False and sign_password.isalpha() == False ):
                    error = '/n Vailid password'

                    if(len(license_plate) > 7 and len(license_plate) < 10 and license_plate.isdigit() == False and license_plate.isalpha() == False and license_exists == False ):
                        usr_write(sign_number, license_plate, sign_password)
                        cookie = make_response(redirect(url_for('info')))
                        cookie.set_cookie('number', sign_number)
                        return cookie
                    else:
                        error = '/n Invailid License Plate'
                else:
                    error = '/n Invailid password'
            else:
                error = '/n Invailid Phone Number'

        return render_template("signup.html", error = error)

    except Exception as e:
        flash(e)
        return render_template("signup.html", error = error)

@app.route('/')
def origianl():
    return redirect(url_for('login')) 

@app.route('/login/', methods=['GET','POST'])
def login():
    error=''
    number_exists = False
    x = usr_read()
    try:
        if request.method == "POST":
            login_number = request.form['phone_number']
            login_password = request.form['password']

            for i in range(len(x)):
                if login_number == x[i][0]:
                    correct_number_row = i
                    number_exists = True
                    break   

            if (number_exists == True):
                error = 'Vailid Phone Number'
                if( login_password == x[correct_number_row][2]):
                    cookie = make_response(redirect(url_for('info')))
                    cookie.set_cookie('number', login_number)
                    return cookie
                else:
                    error = "Invailid password"
            else:
                error = "Number did not sign up"

        return render_template("index.html", error = error)
            
            
    except Exception as e:
        flash(e)
        return render_template("index.html", error = error)

@app.route('/logout/', methods=['GET','POST'])
def logout():
    
    cookie = make_response(redirect(url_for('login')))
    cookie.set_cookie('number', expires=0)
    return cookie





@app.route('/info/')
def info():

    
    
    usrNumber = request.cookies.get('number')

    vr = violated_read(usrNumber)
    rfr= roadfee_read(usrNumber)
    tr = track_read(usrNumber)
    Lplate = usr_read()

    vl = len(vr)
    rfl= len(rfr)
    tl= len(tr)

    for i in range(len(Lplate)):
        if Lplate[i][0]  == usrNumber:
            usr_plate = Lplate[i][1]
            break
    
    return render_template('info.html', vr = vr, rfr = rfr, tr = tr, vl = vl, rfl = rfl, tl = tl, usr_plate = usr_plate, usrName = usrNumber)

if __name__ == "__main__":
    app.secret_key = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    app.run(host="0.0.0.0")
