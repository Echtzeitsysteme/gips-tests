# GIPS Tests

[**GIPS**](https://github.com/Echtzeitsysteme/gips) is an open-source framework for **G**raph-Based (M)**I**LP **P**roblem **S**pecification.
This repository holds some GIPS test projects.


## Setup

* Install [GIPS](https://github.com/Echtzeitsysteme/gips) as described in its [repository](https://github.com/Echtzeitsysteme/gips).
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps. (Please refer to the installation steps of GIPS above.)
* Clone this Git repository to your local machine and import it into Eclipse: *File -> Import -> General -> Existing Projects into Workspace*. Import all projects.
* Build all your projects with the black eMoflon hammer. Sometimes, it is required to trigger a cleaning in Eclipse (*Project -> Clean... -> Clean all projects*).

### Requirements

Currently, all of the tests use the [GLPK](https://www.gnu.org/software/glpk/) ILP solver, which is free and open-source.
Thus, you have to install and configure GLPK or change the configuration of all tests in the [`GlobalTestConfig`](https://github.com/Echtzeitsysteme/gips-tests/blob/main/test.suite.utils/src/test/suite/gips/utils/GlobalTestConfig.java#L24) file from `GLPK` to `GUROBI` or `GLPK`.


## Repository/Project structure

| Project name                     | Type       | Purpose                                                                                        |
| -------------------------------- | ---------- | ---------------------------------------------------------------------------------------------- |
| `gips.ilp.lpoutput`              | GIPSL      | Tests the LP output generation (e.g., logs)                                                    |
| `gips.ilp.timeout.*`             | GIPSL set  | Tests the time limit behaviour of the ILP solvers                                              |
| `gips.multilayeredinheritence.*` | GIPSL      | Tests the correct behaviour of GIPS for multi-layered inheritence in classes                   |
| `gips.null*`                     | GIPSL set  | Test project and metamodel to trigger a NPE bug in GIPS                                        |
| `gips.sort.*`                    | GIPSL set  | A simple test to create a sorted linked-list                                                   |
| `gips.scheduling.*`              | GIPSL set  | Test project and metamodel to plan generic task scheduling                                     |
| `gipsl.all.build.*`              | GIPSL set  | Multiple projects with the same metamodel to test individual language features                 |
| `gipsl.imports.*`                | GIPSL set  | Multiple projects to test the import functionality                                             |
| `gips(l).*bug.*`                 | GIPSL set  | Multiple projects to triggern known bugs (fixed or not fixed)                                  |
| `gipsl.scribble`                 | GIPSL      | Scribble project to debug, e.g., the validator - does not contain tests                        |
| `test.suite.gips`                | Test suite | Test suite with a global test runner to run all tests in this repo                             |
| `test.suite.utils`               | Utilities  | Utilities for the tests that are also needed by the GIPSL projects above                       |


## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for more details.
