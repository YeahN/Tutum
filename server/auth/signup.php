<?php
  ob_start();
  include('/var/www/html/db.php');

  $id= $_GET['id'];
  $pw= $_GET['pw'];
  $name= $_GET['name'];
  $hash = password_hash($pw, PASSWORD_BCRYPT);

  $query= "INSERT INTO users(id, password, name) values('".$id."', '".$hash."', '".$name."');";
  $result= mysqli_query($conn, $query);
  if($result) {
    echo "success";
  } else echo "fail";

  mysqli_close($conn);
?>
