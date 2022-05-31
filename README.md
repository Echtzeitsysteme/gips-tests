# GIPS Tests

[**GIPS**](https://github.com/Echtzeitsysteme/gips) is an open-source framework for **G**raph-Based **I**LP **P**roblem **S**pecification.
This repository holds some GIPS test projects.


## Setup

* Install [GIPS](https://github.com/Echtzeitsysteme/gips) as described in its [repository](https://github.com/Echtzeitsysteme/gips).
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps. (Please refer to the installation steps of GIPS above.)
* Clone this Git repository to your local machine and import it into Eclipse: *File -> Import -> General -> Existing Projects into Workspace*. Import all projects.
* Build all your projects with the black eMoflon hammer. Sometimes, it is required to trigger a cleaning in Eclipse (*Project -> Clean... -> Clean all projects*).

### Requirements

Currently, all of the tests use the [GLPK](https://www.gnu.org/software/glpk/) ILP solver, which is free and open-source.
Thus, you have to install and configure GLPK or change the configuration of all `Model.gipsl` files from `GLPK` to `GUROBI`.


## Repository/Project structure

| Project name                | Type       | Purpose                                                                                        |
| --------------------------- | ---------- | ---------------------------------------------------------------------------------------------- |
| `gipsl.all.build.complex`   | GIPSL      | Contains a large amount of GIPSL code to check successful compilation - does not contain tests |
| `gipsl.all.build.model`     | eMoflon    | Simple (meta)model for testing purposes                                                        |
| `gipsl.all.build.objective` | GIPSL      | Contains a GIPSL project to test against while checking objective return values                |
| `gipsl.all.build.simple`    | GIPSL      | Simple GIPSL project to check execution                                                        |
| `gipsl.scribble`            | GIPSL      | Scribble project to debug, e.g., the validator - does not contain tests                        |
| `test.suite.gips`           | Test suite | Test suite with a global test runner to run all tests in this repo                             |
| `test.suite.utils`          | Utilities  | Utilities for the tests that are also needed by the GIPSL projects above                       |


## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for more details.
