function post_request(username, password, port, page) {
    var xhttp = new XMLHttpRequest();
    var xhhtp_mainpage_POST = new XMLHttpRequest();

    xhhtp_mainpage_POST.open("POST", "http://localhost:8080" + page, true);

    xhttp.open("POST", "http://localhost:"+port, true);
    xhttp.setRequestHeader("Content-type", "text/plain");

    xhhtp_mainpage_POST.send();
    xhttp.send("u="+username+"&p="+password);
    location = location;
}