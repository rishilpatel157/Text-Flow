

let token = localStorage.getItem("jwtToken");
let icon = document.getElementsByClassName("icon")[0]
let username = localStorage.getItem("name")

let id = localStorage.getItem("id")

let logout = document.getElementById("logout")

logout.addEventListener("click",function(){

    localStorage.removeItem('jwtToken');
    localStorage.removeItem('id');
    localStorage.removeItem('name');
    icon.innerHTML = ""
    alert("logout successfully")
    location.href = "/index.html"
    
  
  
  })

let gpt1 = document.getElementById("gpt")
const user = document.querySelector('.user');

if(token!="")
{

icon.innerHTML = null;
icon.innerHTML = username;

}


let selectOption = document.getElementById("selectOption");

selectOption.addEventListener("change",function(){


     if(selectOption.value == "textgeneratation"){
        user.innerHTML = ""

        // showLoadingAnimation();
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
                // hideLoadingAnimation();
                
            });

     }
    else if(selectOption.value != "")
    {
        user.innerHTML = ""
          gpt1.innerHTML = null
         fetchData(token,selectOption.value)

    }



})


async function fetchData(token,selectOption) {
    // showLoadingAnimation();
    const url = `http://localhost:8080/${selectOption}/${id}`;

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

            console.log(data1);
            
                data1.forEach(element => {
                    

                    createTableUser("gpt","response", element.output);
                    // createTableUser();


                });
            // hideLoadingAnimation();
        })
        .catch(function (error) {
            console.error('Fetch error:', error);
            alert("First Login")
            hideLoadingAnimation();
        });
    }  

    function createTableUser(user1,name1,data1) {
          
       
        var userDiv1 = document.getElementsByClassName(user1)[0]
    // Create a new table element
        var table1 = document.createElement("table");

    // Create the table header
    var thead1 = document.createElement("thead");
    var headerRow1 = document.createElement("tr");
    var headerCell1 = document.createElement("th");
    headerCell1.innerHTML = name1; // Replace with the actual name
    headerRow1.appendChild(headerCell1);
    thead1.appendChild(headerRow1);

    // Create the table body
    var tbody1 = document.createElement("tbody");
    var dataRow1 = document.createElement("tr");
    var dataCell1 = document.createElement("td");
    dataCell1.innerHTML = data1; // Replace with the actual data
    dataRow1.appendChild(dataCell1);
    tbody1.appendChild(dataRow1);

    // Add the header and body to the table
    table1.appendChild(thead1);
    table1.appendChild(tbody1);

    userDiv1.appendChild(table1)
    ///////////////////////////////////////////////////////
    // var userDiv2 = document.getElementsByClassName(user2)[0]
    // // Create a new table element
    //     var table2 = document.createElement("table");

    // // Create the table header
    // var thead2 = document.createElement("thead");
    // var headerRow2 = document.createElement("tr");
    // var headerCell2 = document.createElement("th");
    // headerCell2.innerHTML = name2; // Replace with the actual name
    // headerRow2.appendChild(headerCell2);
    // thead2.appendChild(headerRow2);

    // // Create the table body
    // var tbody2 = document.createElement("tbody");
    // var dataRow2 = document.createElement("tr");
    // var dataCell2 = document.createElement("td");
    // dataCell2.innerHTML = data2; // Replace with the actual data
    // dataRow2.appendChild(dataCell2);
    // tbody2.appendChild(dataRow2);

    // // Add the header and body to the table
    // table2.appendChild(thead2);
    // table2.appendChild(tbody2);

    // userDiv2.appendChild(table2)
   
}



function showLoadingAnimation() {
    document.getElementById("loadingAnimation").style.display = "block";
}

function hideLoadingAnimation() {
    document.getElementById("loadingAnimation").style.display = "none";
}


//////////////////////////////


let output = document.getElementsByClassName("output")[0]


function createHistory(data1){
    data1.forEach(element => {
         historyInput = document.createElement("h3");

// Set the "data-id" attribute with the value from element.id
historyInput.setAttribute("data-id", element.id);
    historyInput.classList.add('alert', 'alert-primary'); // Example Bootstrap classes


// Set the inner HTML of the <h3> element
historyInput.innerHTML = element.input.replace(/"/g, '');

// Append the <h3> element to your 'historyCard' (assuming 'historyCard' is defined elsewhere in your code)
user.appendChild(historyInput);
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
        // hideLoadingAnimation();
        
    });
})

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
    var userIdCell = document.createElement("td");
    userIdCell.innerHTML = id;
    var userInputCell = document.createElement("td");
    userInputCell.innerHTML = input;
    var gptOutputCell = document.createElement("td");
    gptOutputCell.innerHTML = output;

    // Append cells to the rows
    userRow.appendChild(userIdCell);
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
