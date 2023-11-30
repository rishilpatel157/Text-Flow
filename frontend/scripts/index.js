const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

let logout = document.getElementById("logout")
var links = document.querySelectorAll('[data-bs-toggle="modal"][data-bs-target="#staticBackdrop"]');

logout.addEventListener("click",function(){

    localStorage.removeItem('jwtToken');
    localStorage.removeItem('id');
    localStorage.removeItem('name');
    links.forEach(function(link) {
      link.setAttribute("data-bs-toggle");
      link.setAttribute("data-bs-target");
    });
    
    icon.innerHTML = ""
    alert("logout successfully")
    location.href = "index.html"
  
  
  })


let token = localStorage.getItem("jwtToken");
let icon = document.getElementsByClassName("icon")[0]
let username = localStorage.getItem("name")


if(token!="")
{

icon.innerHTML = null;
icon.innerHTML = username;

links.forEach(function(link) {
  link.removeAttribute("data-bs-toggle");
  link.removeAttribute("data-bs-target");
});

}


