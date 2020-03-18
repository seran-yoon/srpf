// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
	  "lengthChange": false,
	  "info": false,
	  "order": [[ 0, "desc" ]],
	  "columnDefs": [
		  {
              "targets": [ 5 ],
              "visible": false,
              "searchable": false
          },
		  {
	          "targets": [ 9 ],
	          className: "reqFlag"
	      }
	  ]
  });
  
  var table = $('#dataTable').DataTable();

  $('#dataTable tbody').on( 'click', 'tr', function (event) {

	  $('.reqNo').html(table.row( this ).data()[0]);
	  $('.name').html(table.row( this ).data()[1]);
	  $('.email').html(table.row( this ).data()[2]);
	  $('.reqMessage').html(table.row( this ).data()[3]);
	  $('#respMessage').html(table.row( this ).data()[5]);
	  $('.reqTime').html(table.row( this ).data()[6]);
	  $('#respTime').html(table.row( this ).data()[7]);
	  $('#reqFlag').html(table.row( this ).data()[8]);
	  
  });
  
  $('#dataTable tbody').on( 'click', 'tr td', function () {
	  
	  if (this.className == " reqFlag" && $(this).children().html() == "답변하기") {
		  $("#myModal1").modal();
	  } else if (this.className == " reqFlag" && $(this).children().html() == "답변완료") {
		  $("#myModal2").modal();
	  }
  });
  
  $('#responseBtn').click(function(){
	  var reqNo = $('.reqNo').html();
	  
	  location.href = "response.do?reqNo=" + reqNo; 
  });

  var date = new Date(); 
  $('#nowTime').text((date.getHours() >= 0 && date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":" + (date.getMinutes() >= 0 && date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + " " + (date.getHours >= 0 && date.getHours < 12 ? "AM" : "PM"));
  
});
