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

        const btn = f.canWeCall ? `<div class="col-2" style="text-align:right" id="cus_id_${f.customerId}">
                                                                                <button type="button" class="btn btn-primary" onclick="callCustomer('${f.customerId}')">Make a call</button>
                                                                           </div>` : '';

        res += `

                    <a href="#" class="list-group-item list-group-item-action">
                    <div class="row">
                                    <div class="col-10">

                        <span style="font-weight:bold">${f.name}</span> register at
                        <span style="font-weight:bold">${toDate(f.regDate)}</span> in timezone
                        <span style="font-weight:bold">${f.timeZone}</span>

                        </div>
                                   ${btn}
                                     </div>
                    </a>

                `;
    });
    return res;
}

const toDate = (millis) => {
    if(!millis) {return ''}
    return new Date(millis);
}


const callCustomer = (id) => {
    console.log(id);
    $.ajax({
    		url : `/admin/call/customer?_id=${id}`,
    		method : 'get',
    		dataType : 'json',
    		contentType : "application/json",
    		success : function(data) {
    		    alert('Successfully called');
    		    $('#cus_id_'+id).remove();

    		} , error: function(err) {
    		    alert(`You can't make a call , you already called'`);
    		}

    });

}