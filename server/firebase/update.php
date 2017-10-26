<?php
  include ('/var/www/html/db.php');

  $userId = $_GET["userId"];
  $valid = $_GET["valid"];

  $query = "update fcm set valid = '".$valid."' where userId = '".$userId."';";
  $result = mysqli_query($conn, $query);

  if($result) {
    echo "sucess";
  } else echo "fail";

  mysqli_close($conn);
?>
