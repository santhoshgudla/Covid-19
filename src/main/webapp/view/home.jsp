<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<title>Covid Stats</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
}

body {
  background-color: lightgrey;
  font-family: Arial, Helvetica, sans-serif;
}

/* Style the header */
header {
  background-color: #95afbb;
  padding-top: 5px;
  padding-bottom: 5px;
  margin-left: 100px;
  margin-right: 100px;
  text-align: center;
  font-size: 15px;
  color: white;
}

/* Container for flexboxes */
section {
  display: -webkit-flex;
  display: flex;
  padding-top: 30px;
  padding-left: 150px;
  padding-right: 150px;
  padding-bottom: 30px;
}

table {
  border: 1px solid black;
  border-collapse: collapse;
  font-family: arial, sans-serif;
  width: 100%;
}

th {
  background-color: #95afbb;
  border: 1px solid black;
  text-align: center;
  padding: 5px;
}

td {
  border: 1px solid black;
  text-align: center;
  padding: 5px;
}

/* Responsive layout - makes the menu and the content (inside the section) sit on top of each other instead of next to each other */
@media (max-width: 600px) {
  section {
    -webkit-flex-direction: column;
    flex-direction: column;
  }
}
</style>
</head>
<body>
<header>
  <h2>Covid-19 Stats for Canada</h2>
</header>
<section>
    <table>
      <tr>
        <th>State</th>
        <th>Country</th>
        <th>Total Cases</th>
        <th>No.Of Cases<br/>
            Reported last day.</th>
      </tr>
      <c:forEach items="${covidStats}" var="cs">
         <tr>
            <td>${cs.state}</td>
            <td>${cs.country}</td>
            <td>${cs.totalCase}</td>
            <td>${cs.lastDayCases}</td>
         </tr>
      </c:forEach>
    </table>
</section>
</body>
</html>