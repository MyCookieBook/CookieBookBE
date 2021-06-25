# Getting started
<h2>Enable Plugins</h2>
<ul>
  <li>be sure you enable the plugins in your IDE</li>
        <ul>
              <li>Gherkin</li>
              <li>Cucumber</li>
        </ul> 
</ul>

<h2>Creating feature</h2>
<ul>
  <li> <b>1.</b>  right-click on test/resource/feature folder</li>
  <li> <b>2.</b>  create new file</li>
    <ul> 
      <li> name the new file <b>!WARNING!</b> the name must end on <b>.feature </b> (for example: editRecipe<b>.feature)</b></li>
    </ul>
  <li> <b>3.</b>  add the code for the feature file and create your test scenario</li>
</ul>

<h4>Link</h4>
  <li> IntelliJ: https://www.jetbrains.com/help/idea/creating-feature-files.html </li>

<h2>Run Cucumber Tests</h2>
<ul>
  <li>use the icons in the gutter to either run a specific <b>Scenario</b> or the whole <b>Feature</b></li>
  <li> run Cucumber tests with JUnit by adding <b> cucumber-junit </b> dependency to the project</li>
      <ul>
          <li>Code example</li>

          import io.cucumber.junit.Cucumber;
          import io.cucumber.junit.CucumberOptions;
          import org.junit.runner.RunWith;

          @RunWith(Cucumber.class)
          @CucumberOptions(
                      features = {"classpath:features/BeerCans.feature"},
                      glue = {"com.examples.cucumber"})
          public class RunCucumberTest {
          }
          
      </ul>
</ul>
 <li>In @CucumberOptions, specify the .feature file and the package with step definitions in your project (Glue).</li>
<h4>Link</h4>
  <li>IntelliJ: https://www.jetbrains.com/help/idea/running-cucumber-tests.html</li>
