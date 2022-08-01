/**
 * 
 */
 
 console.log("hi");
 
 //SIDEBAR 
 const ToggleSidebar = () => {
	
	if($(".sidebar").is(":visible")){
		
		$(".sidebar").css("display", "none");
		$(".main-content").css("margin-left", "0%");
		
	}
	else{
		
		$(".sidebar").css("display", "block");
		$(".main-content").css("margin-left", "18%");
		
	}
	
};

//SEARCH FUNCTIONALITY
const search = () => {

	//console.log("Searching...");
	let query = $("#searchBox").val();

	if(query == ''){
		$(".search-result").hide();
	}
	else{
		//Sending Request To Server
		let url = `http://localhost:8086/search/${query}`;

		fetch(url)
			.then((response) => {
				return response.json();
		})
		.then((data) => {
			//console.log(data);

			let text = `<div class='list-group'>`;
 
				data.forEach((contact)=>{

					text+=`<a href='/user/${contact.cid}/contact' class='list-group-item list-group-item-action'> ${contact.name} </a>`;

				});

			text+=`</div`;

			$(".search-result").html(text);
			$(".search-result").show();

		});

	}

};