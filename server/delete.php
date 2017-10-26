<?php
  ob_start();

  include('db.php');

  $invoiceNo = $_GET['invoiceNo'];

  $query = "DELETE FROM invoices WHERE invoiceNo = '".$invoiceNo."';";
  $result = mysqli_query($conn, $query);

  if($result) {
    echo "success";
  } else echo "fail";
  mysqli_close($conn);
?>
