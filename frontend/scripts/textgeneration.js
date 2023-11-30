


let token = localStorage.getItem("jwtToken");
let icon = document.getElementsByClassName("icon")[0]
let username = localStorage.getItem("name")
let id = localStorage.getItem("id")

// Check if the element exists


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

let hibut = document.getElementById("btnhistory")

hibut.addEventListener("click",()=>{
    window.location.reload();
})


var textArea = document.getElementById("textArea");
const gpt = document.querySelector('.gpt');
let outputtext = document.getElementsByClassName("output")[0]

textArea.addEventListener("keydown", function(event) {
    if (event.key === "Enter" && !event.shiftKey && textArea.value !="") {
        // Prevent the default behavior of the Enter key (usually line break)
        event.preventDefault();
        // const istextElement = document.querySelector('.gpt');
        let data = textArea.value;
        // Access the text content of the element
        const text = gpt.textContent.trim();
        
        console.log(outputtext.textContent.trim())
        // Log or manipulate the text as needed
        console.log(text);
if(outputtext.textContent.trim() =="")
{
    createTableUser("user",username, data);
    
    fetchData(data,token,'textgeneration')
    
}
else
{
    let id =  localStorage.getItem("contentId")
    // createTableUser("user",username, data);
   
   showLoadingAnimation();
   const url = `http://localhost:8080/textgen/${id}`;
   
    fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
           }
       })
       .then(function (res) {
           if (!res.ok) {
               throw new Error('Network response was not ok.');
           }
           return res.text();
       })
       .then(function (data1) {
        
           createTableUser("gpt","response", data1);
  
           // console.log(localStorage.setItem("contentId".data1.id))
           hideLoadingAnimation();
       })
       .catch(function (error) {
           console.error('Fetch error:', error);
           hideLoadingAnimation();
           
       });
}
        
      textArea.value = ""  
    }
    
    
});
const user = document.querySelector('.user');



let newChat = document.getElementsByClassName("newChat")[0]
newChat.addEventListener("click",function(){
    gpt.innerHTML = ""
    user.innerHTML = ""
    localStorage.removeItem('contentId');
    

})



    
    
    
    
    
    async function fetchData(data, token,reqUrl) {
        showLoadingAnimation();
        const url = `http://localhost:8080/${reqUrl}`;
        
         fetch(url, {
             method: "POST",
             body: JSON.stringify(data),
             headers: {
                 "Content-Type": "application/json",
                 "Authorization": `Bearer ${token}`
                }
            })
            .then(function (res) {
                if (!res.ok) {
                    throw new Error('Network response was not ok.');
                }
                return res.json();
            })
            .then(function (data1) {
             
               
                createTableUser("gpt","response", data1.output);
                // console.log(data1.i)
                localStorage.setItem("contentId",data1.id)
                // console.log(localStorage.setItem("contentId".data1.id))
                hideLoadingAnimation();
            })
            .catch(function (error) {
                console.error('Fetch error:', error);
                hideLoadingAnimation();
                
            });
        }      
        
        
        
        
        

        function createTableUser(id, input, output) {
            var userDiv = document.getElementsByClassName("user")[0];
            var gptDiv = document.getElementsByClassName("gpt")[0];
        
            // Create a new table element
            var table = document.createElement("table");
        
            // Create the table body
            var tbody = document.createElement("tbody");
        
            // Create the data rows for the user and GPT response
            var userRow = document.createElement("tr");
            var gptRow = document.createElement("tr");
        
            // Create the cells for the user and GPT response
     
            var userInputCell = document.createElement("td");
            userInputCell.innerHTML = input;
            var gptOutputCell = document.createElement("td");
            gptOutputCell.innerHTML = output;
                // var textareadiv = document.createElement("textarea")
                // textareadiv.value
            // Append cells to the rows
            userRow.appendChild(userInputCell);
            gptRow.appendChild(gptOutputCell);
        
            // Append rows to the table body
            tbody.appendChild(userRow);
            tbody.appendChild(gptRow);
        
            // Add the table body to the table
            table.appendChild(tbody);
        
            // Add the table to the corresponding div
            userDiv.appendChild(table);
        }
        

    function showLoadingAnimation() {
        document.getElementById("loadingAnimation").style.display = "block";
    }

    function hideLoadingAnimation() {
        document.getElementById("loadingAnimation").style.display = "none";
    }

   let  historyCard = document.getElementsByClassName("cardhistory")[0];
   

   fetchDatahistory("",token,"history","GET")


       
   function fetchDatahistory(data, token,reqUrl,crud) {
    showLoadingAnimation();
    const url = `http://localhost:8080/history`;
    
     fetch(url, {
         method: "GET",
         headers: {
             "Content-Type": "application/json",
             "Authorization": `Bearer ${token}`
            }
        })
        .then(function (res) {
            if (!res.ok) {
                throw new Error('Network response was not ok.');
            }
            return res.json();
        })
        .then(function (data1) {


            createHistory(data1)
            
            
            // createTableUser("gpt","response", data1);
            hideLoadingAnimation();
        })
        .catch(function (error) {
            console.error('Fetch error:', error);
            hideLoadingAnimation();
            
        });
    }     


    let historyInput = null

function createHistory(data1){
    data1.forEach(element => {
         historyInput = document.createElement("h3");

// Set the "data-id" attribute with the value from element.id
historyInput.setAttribute("data-id", element.id);
    historyInput.classList.add('alert', 'alert-primary'); // Example Bootstrap classes


// Set the inner HTML of the <h3> element
historyInput.innerHTML = element.input.replace(/"/g, '');

// Append the <h3> element to your 'historyCard' (assuming 'historyCard' is defined elsewhere in your code)
historyCard.appendChild(historyInput);
historyInput.addEventListener('click', function(){
    // window.location.reload()
    const url = `http://localhost:8080/history/${this.getAttribute('data-id')}`;
        
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
           }
       })
       .then(function (res) {
           if (!res.ok) {
             throw new Error('Network response was not ok.');
           }
           return res.json();
       })
       .then(function (data) {
        gpt.innerHTML = ""
        user.innerHTML = ""
         data.forEach(function(ele){
            createTableUser("user",ele.textGeneration.customer.name, ele.input);
              createTableUser("gpt","response", ele.output);

         localStorage.setItem("contentId",ele.textGeneration.id)
          })
    
       })
       .catch(function (error) {
        console.error('Fetch error:', error);
        hideLoadingAnimation();
        
    });
})

});
    

}
