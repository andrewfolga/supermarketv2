Overview
========

The design uses the ports and adapters (AKA hexagonal) architectural pattern.

Domain
------

At the heart of the domain is the Cashier who processes the given basket and produces the final receipt. The promotions logic lends itself to easy extension.

Ports/Adapters
--------------

There is currently one promotions repository port wiht no adapter and no secondary ports yet.

Future possible ports/adapters:
 
 - Primary
    - database adapter for persisting promotions
    - database port and adapter for persisting baskets
 - Secondary
    - http port and adapter for comms with the domain (_see future REST resources below_)

Design Notes
------------

The code follows SOLID and tell don't ask methodologies with strong push towards immutability.

It also tries to extend the design where possible like adding new items/promotions without disrupting other classes.

It preserves invariant like in promotions where trigger quantity has to exist or either targetQuantity or targetPrice has to exist.

Proposed future REST resources and uris
=======================================

Create basket
-------------

**Request uri**
```
POST /baskets
```

##### Example : Create basket


**Request url**
```
POST /baskets
```

*No Request Body*

*No Response Body:*

*Response Headers:*
    
    Location: /baskets/1

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|201         | Created basket 1 created                                       |


Get basket
----------

**Request uri**
```
GET /baskets/1
```

##### Example 1: Get basket 1


**Request url**
```
GET /baskets/1
```

*No Request Body*

*Response Body:*
    
    {
        "items" : 
        {
            "BEANS" : {
                "itemType" : "BEANS",
                "priceDefinition" : {
                    "amountPerUnit": "4.50", 
                    "unit": "ITEM"
                },
                "quantity": "2.00"
            }
            "ORANGES" : {
                "itemType" : "ORANGES",
                "priceDefinition" : {
                    "amountPerUnit" : "1.99",
                    "unit" : "KG"
                },
                "quantity": "0.50"
            }
        }
    }

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Returned basket 1                                              |

##### Example 2: Basket 1 does not exist

**Request url**
```
GET /baskets/1
```

*No Request Body*

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|404         | Basket 1 does not exist                                        |


Delete basket
-------------

**Request uri**
```
DELETE /baskets/1
```

##### Example: Delete basket 1


**Request url**
```
DELETE /baskets/1
```

*No Request Body*

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Deleted basket 1                                               |


Add item to basket
------------------

**Request uri**
```
POST /baskets/1/items
```

##### Example: Add item to basket 1

**Request url**
```
POST /baskets/1/items
```

*Request Body*

    {
        "itemType" : "BEANS",
        "priceDefinition" : {
            "amountPerUnit": "4.50", 
            "unit": "ITEM"
        },
        "quantity": "2.00"
    }

*No Response Body:*

*Response Headers:*
    
    Location: /baskets/1/items/1

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|201         | Created item 1 in basket 1                                     |


Get item from basket
--------------------

**Request uri**
```
GET /baskets/1/items/1
```

##### Example 1: Get item 1 from basket 1


**Request url**
```
GET /baskets/1/items/1
```

*No Request Body*

*Response Body:*
    
    {
        "itemType" : "BEANS",
        "priceDefinition" : {
            "amountPerUnit": "4.50", 
            "unit": "ITEM"
        },
        "quantity": "2.00"
    }

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Returned item 1 in basket 1                                    |

##### Example 2: Item 1 in basket 1 does not exist

**Request url**
```
GET /baskets/1/items/1
```

*No Request Body*

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|404         | Item 1 in basket 1 does not exist                              |


Update item in basket
---------------------

**Request uri**
```
PUT /baskets/1/items/1
```

##### Example: Update item in basket

**Request url**
```
PUT /baskets/1/items/1
```

*Request Body*

    {
        "itemType" : "BEANS",
        "priceDefinition" : {
            "amountPerUnit": "3.50", 
            "unit": "ITEM"
        },
        "quantity": "3.00"
    }

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Replaced item 1 in basket 1                                    |


Update a property of item in basket
-----------------------------------

**Request uri**
```
PATCH /baskets/1/items/1
```

##### Example: Update quantity of item 1 in basket 1

**Request url**
```
PATCH /baskets/1/items/1
```

*Request Body*

    {
        "quantity": "5.00"
    }

*No Response Body:*


|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Updated quantity of item 1 in basket 1                         |


Delete item from basket
-----------------------

**Request uri**
```
DELETE /baskets/1/items/1
```

##### Example: Delete item in basket

**Request url**
```
DELETE /baskets/1/items/1
```

*No Request Body*

*No Response Body:*


|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Deleted item 1 in basket 1                                     |


Create promotion
----------------

**Request uri**
```
POST /promotions
```

##### Example 1: Create 3 for 2 promotion


**Request url**
```
POST /promotions
```

*Request Body*

    {
        "triggerQuantity" : "3.00",
        "targetQuantity" : "2.00",
        "itemType" : "BEANS"
        "promotionType" : "THREE_FOR_TWO"
    }

*No Response Body:*

*Response Headers:*
    
    Location: /promotions/1

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|201         | Created promotion 1                                            |


##### Example 2: Create 2 for £1 promotion


**Request url**
```
POST /promotions
```

*Request Body*

    {
        "triggerQuantity" : "2.00",
        "targetPrice" : "1.00",
        "itemType" : "COKE"
        "promotionType" : "TWO_FOR_PRICE"
    }

*No Response Body:*

*Response Headers:*
    
    Location: /promotions/2

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|201         | Created promotion 2                                            |

##### Example 3: Fail to create promotion due to missing triggerQuantity


**Request url**
```
POST /promotions
```

*Request Body*

    {
        "targetPrice" : "1.00",
        "itemType" : "COKE"
        "promotionType" : "TWO_FOR_PRICE"
    }

*No Response Body:*

*No Response Headers:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|400         | Wrong input data: missing trigger quantity                     |


Update property of promotion
----------------------------

**Request uri**
```
PATCH /promotions/1
```

##### Example: Update target price of promotion 1

**Request url**
```
PATCH /promotions/1
```

*Request Body*

    {
        "targetPrice": "3.00"
    }

*No Response Body:*


|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Updated target price of promotion 1                            |


Get promotion
-------------

**Request uri**
```
GET /promotions/1
```

##### Example 1: Get promotion 1


**Request url**
```
GET /promotions/1
```

*No Request Body*

*Response Body:*
    
    {
        "triggerQuantity" : "2.00",
        "targetPrice" : "1.00",
        "itemType" : "COKE"
        "promotionType" : "TWO_FOR_PRICE"
    }

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Returned promotion 1                                            |

##### Example 2: Promotion 1 does not exist

**Request url**
```
GET /promotions/1
```

*No Request Body*

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|404         | Promotion 1 does not exist                                     |


Delete promotion
----------------

**Request uri**
```
DELETE /promotions/1
```

##### Example: Delete promotion 1


**Request url**
```
DELETE /promotions/1
```

*No Request Body*

*No Response Body:*

|Status Code |Description                                                     |
|------------|----------------------------------------------------------------|
|200         | Promotion 1 deleted                                            |
