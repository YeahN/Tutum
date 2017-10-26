<?php
  ob_start();
  include('/var/www/html/db.php');

  $invoiceNo = $_GET['invoiceNo'];

  $query = "SELECT payment FROM invoices WHERE invoiceNo = '".$invoiceNo."';";
  $result = mysqli_query($conn, $query);
  $row = mysqli_fetch_array($result);

  if($row) {
    if($row['payment'] == 0) {
      echo "prepaid";
    } else echo "deferred";
  } else echo "fail";

  mysqli_close($conn);
?>
