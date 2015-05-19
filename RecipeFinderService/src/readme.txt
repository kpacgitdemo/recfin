I took a liberty to interpret the exercise as a logic and coding test rather than building a web project. There for I focus on service layer class RecipeFinderService, which should contain core business logic. In such an architecture the front-end part would be almost agnostic of the result, only collecting	 data from the user and passing result. The service class also would be agnostic to back-end data, there for I implemented a basic method to provide some data from that side of the application.

I modified usedBy date of fridge items to fit it into current time and be able to filter items by expiry date. Also I added one more recipe to test case when two recipes are valid and the one with closer expiry date should be used. I simplified date comparison to checking timestamp, rather whole days, which could be solved using calendar utility.

The implementation is intended to show my logic in solving this problem and choice of tools. I am pretty sure there is number of edge cases, which I have not take into account and perhaps even few potential NullPointerException (for instance missing test file).

I create JUnit test with two basic tests. Again, I am pretty sure there is many more cases to test.

Generally my intention is to do as much as I can in as much as possible time, which means basicly to be finish now. Surely code could be optimised and perhaps an some points better though through. I approach it as a prototype for progress into the interview.