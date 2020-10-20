import java.util.*;

public class MyTwitterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Tweet t1 = new Tweet("OGBobby", "2020-04-28 08:09:50", "What's Good");
		Tweet t2 = new Tweet("Sam", "2020-04-28 09:09:50", "What's Up");
		Tweet t3 = new Tweet("Kale", "2020-04-28 10:09:50", "What's Going on");
		Tweet t4 = new Tweet("James", "2020-04-28 11:09:50", "Hello");
		Tweet t5 = new Tweet("Hallie", "2020-04-28 12:09:50", "Bonjour");
		Tweet t6 = new Tweet("hal", "2020-04-27 13:09:50", "Au revoir");
		Tweet t7 = new Tweet("yuri", "2020-04-27 14:09:50", "Hallo");
		Tweet t8 = new Tweet("Ray", "2020-04-27 15:09:50", "Wie geht's?");
		Tweet t9 = new Tweet("Ellie", "2020-04-27 16:09:50", "Guten Abend");
		Tweet t13 = new Tweet("Ellie", "2020-04-27 16:09:55", "Oi buddy");
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(t1);
		tweets.add(t2);
		tweets.add(t3);
		tweets.add(t4);
		tweets.add(t5);
		tweets.add(t6);
		tweets.add(t7);
		tweets.add(t8);
		tweets.add(t9);
		tweets.add(t13);
		
		ArrayList<String> stopWords = new ArrayList<String>();
		stopWords.add("au");
		stopWords.add("hello");
		stopWords.add("s");
		stopWords.add("wie");
		
		Twitter myTwitter = new Twitter(tweets, stopWords);
		
		Tweet t10 = new Tweet("OGBobby", "2020-04-30 06:09:50", "Je vais au McGill");
		Tweet t11 = new Tweet("Samwise", "2020-03-17 05:08:54", "So");
		Tweet t12 = new Tweet("BobWise", "2020-03-17 05:08:54", "SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO SO");
		Tweet t14 = new Tweet("Soulja Boy", "2020-03-20 04:37:40", "What");
		
		myTwitter.addTweet(t10);
		myTwitter.addTweet(t11);
		myTwitter.addTweet(t12);
		myTwitter.addTweet(t14);
		
		System.out.println(myTwitter.latestTweetByAuthor("Ellie").toString());
		for (String t : myTwitter.trendingTopics()) {
			System.out.println(t);
		}
		if (myTwitter.tweetsByDate("2020-04-31") == null) System.out.println("Yeehaw");
	}

}
