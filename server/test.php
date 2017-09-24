<?php
    include('db.php');
	$invoice= $_GET['invoice'];
	$userId= '';
	$token= '';
	
	$sql = "select userID from invoices where invoiceNo = '".$invoice."';";
	$result = mysqli_query($conn, $sql);
    $row= mysqli_fetch_assoc($result);
	if($row != null){
        $userId = $row['userID'];
    } else echo "db connect fail";

    $sql = "select Token from fcm where userId= '".$userId."';";
    $result = mysqli_query($conn, $sql);
	$row= mysqli_fetch_assoc($result);
    if($row != null){
        $token = $row["Token"];
    }
	mysqli_close($conn);

    $url = "https://fcm.google.com/fcm/send";
	$api_key = 'AAAAOQtucFw:APA91bFtPu9vLG-PyokAf6vj-H0UMAL5qGiZqmyJQAU4eWOczKYI39GzEfJhGgARL3sv7biCvXSZkeM_xU_BCY-RofLj2IUHECpLvLLRc1Suu5CsmsKC6rJnMZulfPR2JEysJbnICs8a';
	$msg = array(
        'body'  => "Complete",
        'title' => 'Tutum',
        );
	$fields = array(
        'to' => $token,
        'notification'  => $msg
        );
	$headers = array(
        'Authorization: key=' . $api_key,
        'Content-Type: application/json'
        );

    $ch = curl_init();
    curl_setopt( $ch,CURLOPT_URL, $url );
    curl_setopt( $ch,CURLOPT_POST, true );
    curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
    curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
    curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
    $result = curl_exec($ch );
var_dump($result);
    curl_close( $ch );
    echo $result;
?>
