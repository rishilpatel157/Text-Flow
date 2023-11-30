


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


var textArea = document.getElementById("textArea");

    textArea.addEventListener("keydown", function(event) {
        if (event.key === "Enter" && !event.shiftKey && textArea.value !="") {
            // Prevent the default behavior of the Enter key (usually line break)
            event.preventDefault();
            let data = textArea.value;

            createTableUser("user",username, data);
            fetchData(data,token)
            
            
        }

        
    });


    async function fetchData(data, token) {
        showLoadingAnimation();
        const url = `http://localhost:8080/textsumm`;
    
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
                console.log(data1);
                
                createTableUser("gpt","response", data1);
                hideLoadingAnimation();
            })
            .catch(function (error) {
                console.error('Fetch error:', error);
                hideLoadingAnimation();
                alert("First Login")
            });
        }      
        

        function createTableUser(user,name,  data ) {
          
       
            var userDiv = document.getElementsByClassName(user)[0]
            userDiv.innerHTML = ""
            // Create a new table element
            var table = document.createElement("table");

        // Create the table header
        var thead = document.createElement("thead");
        var headerRow = document.createElement("tr");
        var headerCell = document.createElement("th");
        headerCell.innerHTML = name; // Replace with the actual name
        headerRow.appendChild(headerCell);
        thead.appendChild(headerRow);

        // Create the table body
        var tbody = document.createElement("tbody");
        var dataRow = document.createElement("tr");
        var dataCell = document.createElement("td");
        dataCell.innerHTML = data; // Replace with the actual data
        dataRow.appendChild(dataCell);
        tbody.appendChild(dataRow);

        // Add the header and body to the table
        table.appendChild(thead);
        table.appendChild(tbody);

        userDiv.appendChild(table)
       
    }

    
    function showLoadingAnimation() {
        document.getElementById("loadingAnimation").style.display = "block";
    }

    function hideLoadingAnimation() {
        document.getElementById("loadingAnimation").style.display = "none";
    }
    
