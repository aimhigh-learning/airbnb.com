$(document).ready(function() {
   customers();
});


const customers = () =>{

	$.ajax({
		url : `/admin/customer/_all`,
		method : 'get',
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			console.log(data);
			$('#main_cus_cont').html(prepareCustomerUiData(data));
		}

	});

}


const prepareCustomerUiData = (cus) =>{
    let res = ``;
    cus?.content?.forEach(f=>{
        res += `<a href="#" class="list-group-item list-group-item-action">
                    <span style="font-weight:bold">${f.name}</span> register at
                    <span style="font-weight:bold">${toDate(f.regDate)}</span>
                </a>`;
    });
    return res;
}

const toDate = (millis) => {
    if(!millis) {return ''}
    return new Date(millis);
}