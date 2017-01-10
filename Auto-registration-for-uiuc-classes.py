from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import NoSuchElementException
import os,time
from selenium.common.exceptions import TimeoutException

driver=webdriver.Chrome()
driver.get('https://apps.uillinois.edu/selfservice/')
driver.implicitly_wait(5)

driver.find_element_by_link_text('University of Illinois at Urbana-Champaign (URBANA)')\
.click()
driver.implicitly_wait(10)

driver.find_element_by_id("ENT_ID").send_keys('xxxxxx')
driver.find_element_by_id("PASSWORD").send_keys('xxxxxxxx')
driver.find_element_by_name("BTN_LOGIN").click()
driver.implicitly_wait(10)

driver.find_element_by_link_text("Registration & Records").click()
driver.implicitly_wait(10)
driver.find_element_by_link_text("Classic Registration").click()
driver.implicitly_wait(10)
driver.find_element_by_link_text("Look-up or Select Classes").click()
driver.implicitly_wait(10)
driver.find_element_by_link_text("I Agree to the Above Statement").click()
driver.implicitly_wait(10)
driver.find_element_by_id("term_input_id").find_element_by_xpath\
("//option[@value='120171']").click()
driver.find_element_by_xpath("//input[@value='Submit']").click()
driver.implicitly_wait(10)
driver.find_element_by_xpath("//option[@value='CS']").click()
driver.find_element_by_xpath("//input[@value='Course Search']").click()
driver.implicitly_wait(10)

driver.find_element_by_xpath("//tbody/tr[51]/td/form/input[@value='View Sections']").click()
driver.implicitly_wait(10) #440 = 36

switch=0

while True:
    print(switch)
    if switch%2==0: 
        driver.implicitly_wait(2)
        try:
            #shit=WebDriverWait(driver,0.5).until(lambda the_driver:\
#the_driver.find_element_by_xpath("//input[@value='50232 120171']").is_displayed())
            shit=driver.find_element_by_xpath("//input[@value='50232 120171']")
            break
        except NoSuchElementException:
            print('no seats available yet, trying 440....')
            driver.back()
            driver.implicitly_wait(2)
            driver.find_element_by_xpath("//tbody/tr[36]/td/form/input[@value='View Sections']").click()
            switch+=1
    else:
        driver.implicitly_wait(2)
        try:
            #shit=WebDriverWait(driver,0.5).until(lambda the_driver:\
#the_driver.find_element_by_xpath("//input[@value='31423 120171']").is_displayed())
            shit=driver.find_element_by_xpath("//input[@value='31423 120171']")
            break
        except NoSuchElementException:
            print('no seats available yet, trying 498....')
            driver.back()
            driver.implicitly_wait(2)
            driver.find_element_by_xpath("//tbody/tr[51]/td/form/input[@value='View Sections']").click()
            switch+=1

if switch%2==0:
    driver.find_element_by_xpath("//input[@value='50232 120171']").click()#440 = 31423
else:
    driver.find_element_by_xpath("//input[@value='31423 120171']").click()
driver.find_element_by_xpath("//input[@value='Register']").click()

print('Course selected')
