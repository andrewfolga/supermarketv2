----------------
Test description
----------------
We value the ability to model problems in software, so our first assessment will be around that subject. The exercise is to model and code a simple supermarket pricing solution. It is inspired on PragDave Supermaket Kata - http://codekata.com/kata/kata01-supermarket-pricing/.

Focus on how to price a supermarket shopping basket and in particular on how to calculate more complex prices such as
- Three cans of beans for the price of two.
- Oranges for £1.99/kilo.
- Two cans of Coke, for £1.


Use the following example of a receiptBuilder to know what data needs to be captured. There's no requirement to actually format or print a receiptBuilder.

    Beans                0.50
    Beans                0.50
    Beans                0.50
    Coke                 0.70
    Coke                 0.70
    Oranges
    0.200 kg @  £1.99/kg 0.40
                        -----
    Sub-total            3.30

    Savings
    Beans 3 for 2       -0.50
    Coke 2 for £1       -0.40
                        -----
    Total savings       -0.90
    -------------------------
    Total to Pay         2.40


Keep it simple and to the point, no web, persistence, ORM layers or frameworks. What we want to see is what classes you create, their responsibility and relationships with other classes. Add a readme file and jot down what assumptions, design decisions and compromises you’ve taken. Please put the code in a git repo of your choice and commit often, so we can see how you grow your code.

Also, please think how to make this flexible, think about immutability, and the S.O.L.I.D principles. https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)

This is the time to show us why you love to write good clean code. We will also run your code over our static analysis tool, so make sure it doesn't have any issues.

You can use TDD, or BDD, or just good selective test coverage. Tests really help us understand your design; what you think are the important business rules that are to be protected when changes come later.

You can use any architectural pattern, eg. Uncle bob's clean architecture, or ports and adapters, or onion, or another of your choice.

---------
For bonus, describe what REST resources and uri would be appropriate.
---------

#Kata01: Supermarket Pricing

This kata arose from some discussions we’ve been having at the DFW Practioners meetings. The problem domain is something seemingly simple: pricing goods at supermarkets.

Some things in supermarkets have simple prices: this can of beans costs $0.65. Other things have more complex prices. For example:

- three for a dollar (so what’s the amount if I buy 4, or 5?)
- $1.99/pound (so what does 4 ounces cost?)
- buy two, get one free (so does the third item have a amount?)

This kata involves no coding. The exercise is to experiment with various models for representing money and prices that are flexible enough to deal with these (and other) pricing schemes, and at the same time are generally usable (at the checkout, for stock management, order entry, and so on). Spend time considering issues such as:

- does fractional money exist?
- when (if ever) does rounding take place?
- how do you keep an audit trail of pricing decisions (and do you need to)?
- are costs and prices the same class of thing?
- if a shelf of 100 cans is priced using “buy two, get one free”, how do you value the stock?

This is an ideal shower-time kata, but be careful. Some of the problems are more subtle than they first appear. I suggest that it might take a couple of weeks worth of showers to exhaust the main alternatives.

#Goal
The goal of this kata is to practice a looser style of experimental modelling. Look for as many different ways of handling the issues as possible. Consider the various tradeoffs of each. What techniques are best for exploring these models? For recording them? How can you validate a model is reasonable?

_Aspects to consider that might edify:_
- _KISS and YAGNI_
- _stakeholder requirements_
- _pricing system dev cost vs. ROI_
- _product return policies_
- _tax system requirements_
- _i18n_
- _currency granularity and conversion_
- _cultural and industry norms_
- _fraud detection_
- _forms of payment_
- _3rd party coupons_
- _forms of POS device_
- _data storage choices_
- _barter and cashier discretion_
- _and last but very important, ease of system maintenance and modification._

Lets compare it from the point of view of someone to sells this stuff.

True amount = E[x], x - random variable, where value is way we amount stuff (e.g. 3 things for $2 - $0.66)

and probability is some normalized attractiveness of such pricing to customers or just what fraction of customers choose to by stuff this way when there are different pricing available.