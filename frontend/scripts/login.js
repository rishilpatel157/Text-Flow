let email = document.getElementById("email");
let passwords = document.getElementById("password");
let btn = document.getElementById("btn");
let login = document.getElementById("login")

let token = localStorage.getItem("jwtToken");
let icon = document.getElementsByClassName("icon")[0]
let username = localStorage.getItem("name")


if(token!="")
{

icon.innerHTML = null;
icon.innerHTML = username;

}

let logout = document.getElementById("logout")

logout.addEventListener("click",function(){

    localStorage.removeItem('jwtToken');
    localStorage.removeItem('id');
    localStorage.removeItem('name');
    icon.innerHTML = ""
    alert("logout successfully")
    location.href = "index.html"
    
  
  
  })


btn.addEventListener("click", function (e) {
    e.preventDefault();
    let username = email.value;
    let password = passwords.value;

    // Add a space after 'Basic'
    let headers = new Headers({
        'Authorization': 'Basic ' + btoa(username + ":" + password)
    });

    fetch('http://localhost:8080/signIn', {
        method: 'GET', // Specify the HTTP method if necessary
        headers: headers,
    }).then(function (response) {
        if (response.ok) {
        const token =    response.headers.get("Authorization");
        localStorage.setItem("jwtToken",token);
        alert("successfully login")
        location.href = "index.html"
            return response.text();
        }
        throw response;
    }).then(function (data) {
        
        let splits = data.split(" ")
        console.log(splits)
        localStorage.setItem("name",splits[0]);
        localStorage.setItem("id",splits[1]);
        
        location.href ="/textgeneration.html"    
        

    }).catch(function (error) {
        console.warn(error);
    });
});