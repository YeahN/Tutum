<?php
  ob_start();
  include('/var/www/html/db.php');

  $invoiceNo = $_GET['invoiceNo'];
  $rfid = $_GET['rfid'];
  $amount = $_GET['amount'];
  $time = gmdate('Y-m-d h:i:s');

  $query = "SELECT * FROM deliveryman WHERE rfid = '".$rfid."';";
  $result = mysqli_query($conn, $query);
  $row = mysqli_fetch_array($result);
  if($row) {
    $query = "SELECT userID, companyCode FROM invoices WHERE invoiceNo = '".$invoiceNo."';";
    $result = mysqli_query($conn, $query);
    $row = mysqli_fetch_array($result);
    $userID = $row['userID'];
    $companyCode = $row['companyCode'];

    $query = "SELECT point FROM users WHERE id = '".$userID."';";
    $result = mysqli_query($conn, $query);
    $row = mysqli_fetch_array($result);
    if($row['point'] >= $amount) {
      $query = "SELECT name FROM companies WHERE code = '".$companyCode."';";
      $result = mysqli_query($conn, $query);
      $row = mysqli_fetch_array($result);

      $query = "INSERT INTO billing(companyCode, invoiceNo, rfid, amount, time) VALUES('".$companyCode."', '".$invoiceNo."', '".$rfid."', '".$amount."', '".$time."');";
      $result1 = mysqli_query($conn, $query);

      $query = "INSERT INTO pay_history(userID, sign, amount, detail, time) VALUES('".$userID."', '-', '".$amount."', '".$row['name']."', '".$time."');";
      $result2 = mysqli_query($conn, $query);

      $query = "UPDATE users SET point = point - $amount WHERE id = '".$userID."';";
      $result3 = mysqli_query($conn, $query);

      if(!$result1) {
        echo "fail: INSERT INTO billing";
      } else if(!$result2) {
        echo "fail: INSERT INTO pay_history";
      } else if(!$result3) {
        echo "fail: UPDATE users";
      } else echo "success";
    } else echo "insufficient";
  } else echo "fail: rfid";

  mysqli_close($conn);
?>
