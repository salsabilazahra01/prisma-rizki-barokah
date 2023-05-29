//set datetime-input to max value
var input = document.querySelector('.datetime-input');
var currentDate = new Date();
var year = currentDate.getFullYear();
var month = String(currentDate.getMonth() + 1).padStart(2, '0');
var day = String(currentDate.getDate()).padStart(2, '0');
var hours = String(currentDate.getHours()).padStart(2, '0');
var minutes = String(currentDate.getMinutes()).padStart(2, '0');
var formattedDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
input.setAttribute('max', formattedDateTime);