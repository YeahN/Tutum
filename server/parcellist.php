<?php
  ob_start();
  include('db.php');

  $id = $_GET['id'];
  $query = "SELECT invoices.companyCode, companies.name, invoices.invoiceNo FROM companies, invoices WHERE invoices.userId='".$id."' AND companies.code=invoices.companyCode;";
  $result = mysqli_query($conn, $query);

  $response= array();
  while($row = mysqli_fetch_array($result)) {
    array_push($response, array("companyCode" => $row[0], "companyName" => $row[1], "invoiceNo" => $row[2]));
  }
  echo json_encode(array("response" => $response));

  mysqli_close($conn);
?>
