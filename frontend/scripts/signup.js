let username = document.getElementById("username");
let email = document.getElementById("email");
let password = document.getElementById("password");
let repassword = document.getElementById("repassword");


let token = localStorage.getItem("jwtToken");
let icon = document.getElementsByClassName("icon")[0]
let uname = localStorage.getItem("name")


if(token!="")
{

icon.innerHTML = null;
icon.innerHTML = uname;

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

btn.addEventListener("click",function(e){
    e.preventDefault();

    obj = {};
    obj.name = username.value;
    obj.email = email.value
 obj.password = password.value

 const url = `http://localhost:8080/customers`;

 fetch(url,{
   method: "POST",
   body: JSON.stringify(obj),
   headers: {
       "Content-Type": "application/json"
   }
})
   .then(function (res) {
       if (!res.ok) {
           throw new Error('Network response was not ok.');
       }
       return res.text();
   })
   .then(function (data1) {
    console.log(data1)
    location.href ="/login.html"   
    // href.location = "/login.html"
    alert(data1) 
    // You can further process the response data here
   })
   .catch(function (error) {
       console.error('Fetch error:', error);
       // Handle errors here, e.g., display an error message to the user
   });
})