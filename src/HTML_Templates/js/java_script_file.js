function post_request(username, password, port, page) {
    var process_socket = new XMLHttpRequest();
    var login_page = new XMLHttpRequest();

    login_page.open("POST", "http://localhost:8080" + page, true);

    process_socket.open("POST", "http://localhost:"+port, true);
    process_socket.setRequestHeader("Content-type", "text/plain");

    login_page.send();
    process_socket.send("u="+username+"&p="+password);
    location = location;
}

function test_btn(){
    var test = new XMLHttpRequest();

    test.open("POST", "http://localhost:8083", true);
    test.send("btn1=ON");
    location = location;

}