<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-widt,initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">


<script defer src="script.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styling/Bank.css">
<title>Operations</title>

</head>
<body>
<div class="container">

<h2 class="jsw" >Welcome to JSW BANK</h2>
<p class="jsw1">Click on the operation you want to perform!</p>

<button class="btn btn1" onclick="openForm()">WITHDRAW</button>
<div class="form-popup" id="myForm">
<form action="${pageContext.request.contextPath}/withdraw" method="post" class="form-container">
    <h1>Enter Amount</h1>

    <label for="Amount"><b>Amount: </b></label>
    <input type="number" placeholder="Enter Amount" name="amount" required>

    <button type="submit" class="btn">Enter</button>
    <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
  </form> 
</div>

<button class="btn btn2" onclick="openForm1()">DEPOSIT</button>
<div class="form-popup" id="myForm1">
<form action="${pageContext.request.contextPath}/deposit" method="post" class="form-container"> <%---DEPOSIT SERVLET INVOKED --%>
    <h1>Enter Amount</h1>

    <label for="Amount"><b>Amount: </b></label>
    <input type="number" placeholder="Enter Amount" name="amount" required>

    <button type="submit" class="btn">Enter</button>
    <button type="button" class="btn cancel" onclick="closeForm1()">Close</button>
  </form>
</div>

<form action="balance" method="get">
	<button type="submit" class="btn btn4">CHECK BALANCE</button>
</form>

<form action="bankstatement" method="get">
	<button type="submit" class="btn btn4">BANK TRANSACTIONS</button>
</form>

<form action="logout" method="get">
	<button type="submit" class="btn btn4">LOGOUT</button>
</form>

</div>

<script>
function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}

function openForm1() {
	  document.getElementById("myForm1").style.display = "block";
	}

	function closeForm1() {
	  document.getElementById("myForm1").style.display = "none";
	}
	
	function openForm2() {
		  document.getElementById("myForm2").style.display = "block";
		}

		function closeForm2() {
		  document.getElementById("myForm2").style.display = "none";
		}	
</script>

</body>
</html>
