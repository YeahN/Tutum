<?php
  ob_start();
  include('db.php');

  $id = $_GET['id'];
  $query = "SELECT sign, amount, detail, time FROM pay_history WHERE userId='".$id."';";
  $result= mysqli_query($conn, $query);
  $response= array();

  while($row = mysqli_fetch_array($result)) {
    array_push($response, array("sign" => $row[0], "amount" => $row[1], "detail" => $row[2], "time" => $row[3]));
  }

  echo json_encode(array("response" => $response));
  mysqli_close($conn);
?>
