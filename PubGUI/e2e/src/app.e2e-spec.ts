import { AppPage } from './app.po';
import { browser, logging, by } from 'protractor';
import { element } from '@angular/core/src/render3';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Title should be PubG Tour', () => {
    page.navigateTo();
    browser.driver.manage().window().maximize();
    expect(browser.getTitle()).toEqual('PubG Tour');
  });

  it('It should redirect to /home/tournaments on opening', () => {
    expect(browser.getCurrentUrl()).toContain('home/tournaments');
  });

  it('It should redirect to /login when pressing Login/Register button', () => {
    browser.element(by.id('registerLoginButton')).click();
    expect(browser.getCurrentUrl()).toContain('/login');
  });

  it('It should redirect to /register when pressing Register button on login page', () => {
    browser.element(by.id('registerButton')).click();
    expect(browser.getCurrentUrl()).toContain('/register');
  });

  it('It should be able to register user with username "test-user"', () => {
    browser.element(by.id('firstName')).sendKeys('Test');
    browser.element(by.id('lastName')).sendKeys('User');
    browser.element(by.id('userName')).sendKeys('test-user');
    browser.element(by.id('password')).sendKeys('test123');
    browser.element(by.id('mainRegisterButton')).click();
    expect(browser.getCurrentUrl()).toContain('/login');
  });

  it('It should be able to login', () => {
    browser.element(by.id('userName')).sendKeys('test-user');
    browser.element(by.id('password')).sendKeys('test123');
    browser.element(by.id('mainLoginButton')).click();
    browser.element(by.id('favouritiesButton')).click();
    expect(browser.getCurrentUrl()).toContain('home/favourities');
  });

  it('It should be able to add matches to favourities', () => {
    browser.element(by.id('tournamentsButton')).click();
    browser.driver.sleep(3000);
    const tournamentDetailCards = browser.element.all(by.css('.detail-card'));
    tournamentDetailCards.get(0).element(by.id('viewMatchesButton')).click();
    browser.driver.sleep(3000);
    const matchDetailCards = browser.element.all(by.css('.detail-card'));
    matchDetailCards.get(0).element(by.id('viewDetailsButton')).click();
    browser.driver.sleep(3000);
    browser.element(by.id('addToFavouritiesButton')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('favouritiesButton')).click();
    browser.driver.sleep(2000);
    const isPresent = browser.element(by.className("detail-card")).isPresent();
    expect(isPresent).toBe(true);
  });

  it('It should be able to update matches in favourites', () => {
    browser.element(by.id('viewFavouritiesMatchSummaryButton')).click();
    browser.element(by.id('matchCommentsBox')).sendKeys('test');
    browser.element(by.id('updateFavouritiesButton')).click();
    browser.driver.sleep(1000);
    browser.element(by.id('favouritiesSummaryCloseButton')).click();
    browser.element(by.id('tournamentsButton')).click();
    browser.driver.sleep(3000);
    browser.element(by.id('favouritiesButton')).click();
    browser.element(by.id('viewFavouritiesMatchSummaryButton')).click();
    const commentText = browser.element(by.id('matchCommentsBox')).getAttribute('value');
    expect(commentText).toBe('test');
  });

  it('It should be able to delete matches from favourities', () => {
    browser.element(by.id('favouritiesSummaryCloseButton')).click();
    browser.element(by.id('deleteFromFavouritiesButton')).click();
    const isPresent = browser.element(by.className("detail-card")).isPresent();
    expect(isPresent).toBe(false);
  })

  it('It should be able to logout', () => {
    browser.element(by.id('logoutButton')).click();
    expect(browser.getCurrentUrl()).toContain('/login');
  })

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
