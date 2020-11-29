
## Testing using curl 

Get account:

```
curl -s --location --request GET 'http://localhost:8000/account/v1/17892' | jq

response is (200):
{
  "createDate": "2020-11-29T11:12:09.320+0000",
  "owner": "Kerem Karaca",
  "accountNumber": "17892",
  "balance": 0,
  "transactions": []
}
```

To pay phone bill (insufficient balance):
```
curl -s --location --request POST 'http://localhost:8000/account/v1/phone_bill/17892' \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' \
    --data-raw '{
        "payee": "Turk Telekom",
        "phoneNumber": "5567778885",
        "amount": 150.0
    }'

response is (400):
InsufficientBalance
```

To deposit money into an account:

```
curl -s --location --request POST 'http://localhost:8000/account/v1/credit/17892' \
    --header 'Content-Type: application/json' \
    --header 'Accept: application/json' \
    --data-raw '    {
            "amount": 1000.0
        }' | jq

response is (200):
{
  "status": "OK",
  "approvalCode": "951d1b84-8e6b-4d5e-931c-6ee6149fd05b"
}
```

To pay phone bill:
```
curl -s --location --request POST 'http://localhost:8000/account/v1/phone_bill/17892' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
        "payee": "Turk Telekom",
        "phoneNumber": "5567778885",
        "amount": 150.0
    }'

response is (200):
{
    "status":"OK",
    "approvalCode":"f289ee17-3e1f-4cd2-b8f0-d9d9159f2526"
}
```

To debit:
```
curl -s --location --request POST 'http://localhost:8000/account/v1/debit/17892' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '    {
        "amount": 50.0
    }' | jq

response is (200):
{
  "status": "OK",
  "approvalCode": "bfdd96a5-2ba3-4f88-8acf-7ce74bf9727a"
}
```

Get account info:
```
curl -s --location --request GET 'http://localhost:8000/account/v1/17892' | jq

response is (200):
{
  "createDate": "2020-11-29T11:38:43.255+0000",
  "owner": "Kerem Karaca",
  "accountNumber": "17892",
  "balance": 800,
  "transactions": [
    {
      "date": "2020-11-29T11:37:11.575+0000",
      "amount": 50,
      "type": "WithdrawalTransaction",
      "approvalCode": "bfdd96a5-2ba3-4f88-8acf-7ce74bf9727a"
    },
    {
      "date": "2020-11-29T11:36:18.310+0000",
      "amount": 150,
      "type": "PhoneBillPaymentTransaction",
      "approvalCode": "f289ee17-3e1f-4cd2-b8f0-d9d9159f2526",
      "payee": "Turk Telekom",
      "phoneNumber": "5567778885"
    },
    {
      "date": "2020-11-29T11:35:58.226+0000",
      "amount": 1000,
      "type": "DepositTransaction",
      "approvalCode": "951d1b84-8e6b-4d5e-931c-6ee6149fd05b"
    }
  ]
}
```

