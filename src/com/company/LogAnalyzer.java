package com.company;

import java.util.*;
import edu.duke.*;

public class LogAnalyzer {

     private ArrayList<LogEntry> records;

     // Constructor
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }

     // Read a log file and save the entries to the array
     public void readFile(String fileName) {
         FileResource file = new FileResource(fileName);

         for(String entry : file.lines()){
             LogEntry newRecord = WebLogParser.parseEntry(entry);
             records.add(newRecord);
         }
     }

     // Count unique IP addresses
     public int countUniqueIPs(){
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for(LogEntry le : records){
             String ipAddr = le.getIpAddress();
             if(!uniqueIPs.contains(ipAddr)){
                 uniqueIPs.add(ipAddr);
             }
         }
         System.out.println("Number of unique IPs are: " + uniqueIPs.size());
         return uniqueIPs.size();
     }

     // Print all log entries
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }

     // Print all log entries that has status code higher than Num
     public void printAllHigherThanNum(int num){
         for(LogEntry le : records){
             int ipStatus = le.getStatusCode();
             if(ipStatus > num){
                 System.out.println(le);
             }
         }

     }

     // Find unique IPs from one specific day
        // Date format = MMM DD => Eks: "Dec 05"
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
         ArrayList<String> uniqueIPs = new ArrayList<String>();

         for(LogEntry le : records){
             String date = le.getAccessTime().toString();
             String monthDay = date.substring(4,10);
             String ipAddr = le.getIpAddress();

             if (monthDay.equals(someday) && !uniqueIPs.contains(ipAddr)){
                 System.out.println(le);
                 uniqueIPs.add(ipAddr);
             }
         }
         System.out.println("Uniques in one day is: " + uniqueIPs.size());
         return uniqueIPs;

     }

    // Find unique IPs with status code within a range
    public int countUniqueIPsInRange(int low, int high){
        ArrayList<String> uniqueIPs = new ArrayList<String>();

        for(LogEntry le : records){
            int statusCode = le.getStatusCode();
            String ipAddr = le.getIpAddress();

            if (statusCode >= low && statusCode <= high && !uniqueIPs.contains(ipAddr)){
                System.out.println(le);
                uniqueIPs.add(ipAddr);
            }
        }
        System.out.println("Number of unique IPs in range is: " + uniqueIPs.size());
        return uniqueIPs.size();
    }

    // Count visits for each IP adress
    public HashMap<String, Integer> countVisitsPerIP(){
         HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for(LogEntry le : records){
            String ip = le.getIpAddress();

            if(!counts.containsKey(ip)){
                counts.put(ip, 1);
            } else {
                counts.put(ip, counts.get(ip) + 1);
            }
        }

        for(String ipAddr : counts.keySet()){
            int ipCount = counts.get(ipAddr);
            System.out.println(ipAddr + " = " + ipCount);
        }
        System.out.println("Unique IP adresses is: " + counts.size());
        return counts;
    }


    public int mostNumberVisitsByIP(HashMap<String, Integer> map){
         int highestSoFar = 0;

         for(String ipAddr : map.keySet()){
             int visits = map.get(ipAddr);
             if(visits > highestSoFar){
                 highestSoFar = visits;
             }
         }
        System.out.println("Highest so far is: " + highestSoFar);
         return highestSoFar;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map){
         int maxVisits = mostNumberVisitsByIP(map);
         ArrayList<String> maxVisitors = new ArrayList<String>();

         for(String ipAddr : map.keySet()){
             int visits = map.get(ipAddr);
             if(visits == maxVisits){
                 maxVisitors.add(ipAddr);
             }
         }
         System.out.println(maxVisitors);
         return maxVisitors;
    }

    public HashMap<String, ArrayList<String>> iPsForDays(){
         HashMap<String, ArrayList<String>> dayMap = new HashMap<String, ArrayList<String>>();

         for(LogEntry le : records){
            String date = le.getAccessTime().toString();
            String monthDay = date.substring(4,10);

            if(!dayMap.containsKey(monthDay)){
                ArrayList<String> ipAdrr = new ArrayList<String>();
                ipAdrr.add(le.getIpAddress());
                dayMap.put(monthDay, ipAdrr);
            } else {
                ArrayList<String> ipAdrr = dayMap.get(monthDay);
                ipAdrr.add(le.getIpAddress());
            }
         }

         System.out.println(dayMap);
         return dayMap;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> ipDays){
         int mostVisits = 0;
         String mostVisitedDay = "";

         for(String day : ipDays.keySet()){
             int visitDay = ipDays.get(day).size();

             if(visitDay > mostVisits){
                 mostVisits = visitDay;
                 mostVisitedDay = day;
             }
         }

         System.out.println("Most visited day was: " + mostVisitedDay);
         return mostVisitedDay;
    }


    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> iPsForDays, String date){
         HashMap<String, Integer> visits = new HashMap<>();

        for(String day : iPsForDays.keySet()){
            if(day.equals(date)){
                ArrayList<String> visitorArray = iPsForDays.get(day);

                for(String visitor : visitorArray){
                    if(visits.containsKey(visitor)){
                        visits.put(visitor, visits.get(visitor) + 1);
                    } else {
                        visits.put(visitor, 1);
                    }
                }
            }
        }

        ArrayList<String> mostVisits = iPsMostVisits(visits);
        // System.out.println(mostVisits);
        return mostVisits;
    }
}
