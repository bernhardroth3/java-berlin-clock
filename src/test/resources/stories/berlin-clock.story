Story: The Berlin Clock

Meta:
@scope interview

Narrative:
    As a clock enthusiast
    I want to tell the time using the Berlin Clock
    So that I can increase then number of ways that I can read the time

Scenario: Midnight
When the time is 00:00:00
Then the clock should look like
Y
OOOO
OOOO
OOOOOOOOOOO
OOOO

Scenario: Mid morning
When the time is 09:30:01
Then the clock should look like
O
ROOO
RRRR
YYRYYROOOOO
OOOO

Scenario: Middle of the afternoon
When the time is 13:17:01
Then the clock should look like
O
RROO
RRRO
YYROOOOOOOO
YYOO

Scenario: Just before midnight
When the time is 23:59:59
Then the clock should look like
O
RRRR
RRRO
YYRYYRYYRYY
YYYY

Scenario: Midnight
When the time is 24:00:00
Then the clock should look like
Y
RRRR
RRRR
OOOOOOOOOOO
OOOO

!-- Added tests for boudaries
Scenario: One minute after midnight (zero)
When the time is 00:01:00
Then the clock should look like
Y
OOOO
OOOO
OOOOOOOOOOO
YOOO

Scenario: One minute after midnight (24)
When the time is 24:01:00
Then the clock should look like
Y
RRRR
RRRR
OOOOOOOOOOO
YOOO

Scenario: Lamptest i.e. all lights on
When the time is 24:59:58
Then the clock should look like
Y
RRRR
RRRR
YYRYYRYYRYY
YYYY

!-- Added tests for errors
Scenario: Missing minutes and seconds values
When the time is 30
Then the clock should look like
ERROR: Invalid time format. Expected: hh:mm:ss - Received: 30

Scenario: Missing seconds value
When the time is 30:45
Then the clock should look like
ERROR: Invalid time format. Expected: hh:mm:ss - Received: 30:45

Scenario: Incorrect delimiter
When the time is 13;17#01
Then the clock should look like
ERROR: Invalid time format. Expected: hh:mm:ss - Received: 13;17#01

Scenario: Hour out of bounds
When the time is 30:00:02
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 30:00:02

Scenario: Hour out of bounds by one
When the time is -1:00:02
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: -1:00:02

Scenario: Hour out of bounds by one
When the time is 25:00:02
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 25:00:02

Scenario: Minutes out of bounds
When the time is 00:-10:07
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:-10:07

Scenario: Minutes out of by one
When the time is 00:-1:07
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:-1:07

Scenario: Minutes out of by one
When the time is 00:60:07
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:60:07

Scenario: Seconds out of bounds
When the time is 00:10:85
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:10:85

Scenario: Seconds out of bounds by one
When the time is 00:10:60
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:10:60

Scenario: Seconds out of bounds by one
When the time is 00:10:-1
Then the clock should look like
ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: 00:10:-1

Scenario: Invalid hour
When the time is x34:00:00
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: 'x34:00:00'

Scenario: Invalid minutes
When the time is 00:mm:00
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '00:mm:00'

Scenario: Invalid seconds
When the time is 00:10:bb
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '00:10:bb'

Scenario: Invalid seconds with leading blanks
When the time is 00:10: 20  
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '00:10: 20'

Scenario: Invalid minutes with leading blanks
When the time is 00: 10:20  
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '00: 10:20'

Scenario: Invalid hours with trailling characters
When the time is 00&:10:20  
Then the clock should look like
ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '00&:10:20'

