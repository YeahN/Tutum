<?php
  ob_start();
  include('db.php');

  $userID = $_GET['userID'];
  $companyCode = $_GET['companyCode'];
  $invoiceNo = $_GET['invoiceNo'];
  $payment = $_GET['payment'];

  $query= "insert into invoices(userID, companyCode, invoiceNo, payment) values('".$userID."', '".$companyCode."', '".$invoiceNo."', '".$payment."');";
  $result= mysqli_query($conn, $query);

  if($result){
    echo "success";
  } else echo "fail";
  mysqli_close($conn);
?>
